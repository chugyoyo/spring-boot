/**
 * 一个通过线程池执行提交任务的 {@link ExecutorService} 实现，
 * 通常通过 {@link Executors} 工厂方法进行配置。
 *
 * <p>线程池解决两大问题：
 * 1. 通过减少任务调用的开销，提升大量异步任务的执行性能
 * 2. 对执行任务时消耗的资源（包括线程）进行限制和管理
 * 每个 {@code ThreadPoolExecutor} 还会维护基础统计信息（如已完成任务数）
 *
 * <p>为适应多种场景，本类提供大量可调参数和扩展点。
 * 但建议优先使用 {@link Executors} 的预配置工厂方法：
 * - {@link Executors#newCachedThreadPool}（无界线程池，自动回收空闲线程）
 * - {@link Executors#newFixedThreadPool}（固定大小线程池）
 * - {@link Executors#newSingleThreadExecutor}（单后台线程）
 * 如需手动配置，请参考以下指南：
 *
 * <dl>
 *
 * <dt>核心线程数与最大线程数</dt>
 *
 * <dd>{@code ThreadPoolExecutor} 根据以下规则动态调整线程池大小：
 * - 当通过 {@link #execute(Runnable)} 提交新任务时：
 *   - 若运行线程数 < corePoolSize：即使有空闲线程也创建新线程
 *   - 若 corePoolSize ≤ 运行线程数 < maximumPoolSize：仅当队列满时创建新线程
 * - 设置 corePoolSize = maximumPoolSize 可创建固定大小线程池
 * - 设置 maximumPoolSize = Integer.MAX_VALUE 允许无限线程数
 * - 核心参数可通过 {@link #setCorePoolSize} 和 {@link #setMaximumPoolSize} 动态调整</dd>
 *
 * <dt>按需创建线程</dt>
 *
 * <dd>默认情况下，核心线程也仅在任务到达时创建。
 * 可通过 {@link #prestartCoreThread} 或 {@link #prestartAllCoreThreads} 预启动线程。
 * 若使用非空队列构造线程池，建议预启动线程。</dd>
 *
 * <dt>线程创建</dt>
 *
 * <dd>通过 {@link ThreadFactory} 创建新线程。
 * 默认使用 {@link Executors#defaultThreadFactory}，创建同线程组、普通优先级、非守护线程。
 * 自定义 ThreadFactory 可修改线程名称、组、优先级等属性。
 * 若 ThreadFactory 返回 null，线程池将无法执行任务。
 * 工作线程需拥有 "modifyThread" 运行时权限，否则可能导致配置延迟生效或关闭异常。</dd>
 *
 * <dt>线程存活时间</dt>
 *
 * <dd>当线程数 > corePoolSize 时，空闲超过 keepAliveTime 的线程将被终止。
 * 可通过 {@link #setKeepAliveTime} 动态调整。
 * 设置 {@code Long.MAX_VALUE} 纳秒可禁用空闲线程回收。
 * 默认策略不回收核心线程，但可通过 {@link #allowCoreThreadTimeOut(true)} 启用核心线程超时回收（需 keepAliveTime > 0）。</dd>
 *
 * <dt>队列策略</dt>
 *
 * <dd>队列选择与线程池大小策略密切相关：
 * <ul>
 *   <li>运行线程数 < corePoolSize：优先创建新线程而非入队</li>
 *   <li>运行线程数 ≥ corePoolSize：优先入队而非创建新线程</li>
 *   <li>无法入队时：创建新线程（不超过 maximumPoolSize），否则触发拒绝策略</li>
 * </ul>
 *
 * 三种典型队列策略：
 * <ol>
 *   <li><em>直接传递队列（如 {@link SynchronousQueue}）</em>：
 *      - 无缓冲队列，若无空闲线程立即创建新线程
 *      - 需设置较大的 maximumPoolSize 避免拒绝
 *      - 适用于避免死锁的场景，但可能导致线程数激增</li>
 *
 *   <li><em>无界队列（如 {@link LinkedBlockingQueue}）</em>：
 *      - 固定使用 corePoolSize 个线程，maximumPoolSize 无效
 *      - 适用于任务完全独立的场景（如 Web 服务器）
 *      - 可能引发队列无限增长</li>
 *
 *   <li><em>有界队列（如 {@link ArrayBlockingQueue}）</em>：
 *      - 需谨慎权衡队列大小与最大线程数
 *      - 大队列+小线程池：节省资源但吞吐量低
 *      - 小队列+大线程池：CPU 利用率高但调度开销大
 *      - 适用于 I/O 密集型任务</li>
 * </ol></dd>
 *
 * <dt>拒绝策略</dt>
 *
 * <dd>当线程池关闭或达到资源上限时，新提交任务将被拒绝。
 * {@link #execute} 方法会调用 {@link RejectedExecutionHandler} 处理拒绝任务。
 * 提供四种内置策略：
 * <ol>
 *   <li>{@link ThreadPoolExecutor.AbortPolicy}：默认策略，抛出 {@link RejectedExecutionException}</li>
 *   <li>{@link ThreadPoolExecutor.CallerRunsPolicy}：由调用者线程直接执行任务</li>
 *   <li>{@link ThreadPoolExecutor.DiscardPolicy}：静默丢弃被拒任务</li>
 *   <li>{@link ThreadPoolExecutor.DiscardOldestPolicy}：丢弃队列最旧任务并重试提交</li>
 * </ol>
 * 自定义策略时需注意与容量/队列策略的兼容性。</dd>
 *
 * <dt>钩子方法</dt>
 *
 * <dd>可通过重写以下 protected 方法扩展功能：
 * - {@link #beforeExecute(Thread, Runnable)}：任务执行前调用（如初始化 ThreadLocal）
 * - {@link #afterExecute(Runnable, Throwable)}：任务执行后调用（如收集统计信息）
 * - {@link #terminated}：线程池完全终止后调用
 * 注意：若钩子方法抛出异常，可能导致工作线程意外终止</dd>
 *
 * <dt>队列维护</dt>
 *
 * <dd>{@link #getQueue()} 方法可用于监控队列，但强烈不建议用于其他目的。
 * 提供 {@link #remove(Runnable)} 和 {@link #purge} 方法帮助清理取消的任务。</dd>
 *
 * <dt>终止处理</dt>
 *
 * <dd>当线程池不再被引用且无活动线程时，将自动关闭。
 * 为确保资源回收，建议：
 * - 设置 keepAliveTime = 0
 * - 允许核心线程超时
 * - 设置 corePoolSize = 0</dd>
 *
 * </dl>
 *
 * <p><b>扩展示例</b>：通过重写钩子方法实现暂停/恢复功能
 * <pre>{@code
 * class PausableThreadPoolExecutor extends ThreadPoolExecutor {
 *   private boolean isPaused;
 *   private ReentrantLock pauseLock = new ReentrantLock();
 *   private Condition unpaused = pauseLock.newCondition();
 *
 *   // 构造方法
 *   protected void beforeExecute(Thread t, Runnable r) {
 *     super.beforeExecute(t, r);
 *     pauseLock.lock();
 *     try {
 *       while (isPaused) unpaused.await(); // 暂停时阻塞
 *     } catch (InterruptedException ie) {
 *       t.interrupt();
 *     } finally {
 *       pauseLock.unlock();
 *     }
 *   }
 *
 *   public void pause() { isPaused = true; }  // 暂停
 *   public void resume() {                    // 恢复
 *     pauseLock.lock();
 *     try {
 *       isPaused = false;
 *       unpaused.signalAll();
 *     } finally {
 *       pauseLock.unlock();
 *     }
 *   }
 * }}</pre>
 *
 * @since 1.5
 * @author Doug Lea
 */
package org.springframework.boot.launchscript.threadpool;