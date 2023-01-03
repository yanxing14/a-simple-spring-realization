Java课的作业，简单实现了一个mvc服务器。

DispathcherServlet类：
负责调度。
initialize：扫描获得所有controller，构建映射关系
doGet：读取jsp，根据路径修改jsp显示
解耦合：需要的路径从httpServer的一个方法获得

HttpServer类：
服务器
getPath：用getInputStream获取Reuquest，得到路径
setRespContext：解耦合，接收servlet传来的jsp字符串
response：加上response header传给客户端输出流
run：多线程实现

RequestMappingUtil
实现路径和controller方法的对应
