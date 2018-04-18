## someThinking
1. 视图的比例缩放，所见为鼠标位置，缩放后如何求取选取的真正区域，判断点是否在区域内-->thinking
2. 数据库异步操作，离线过程，粒度大小的选择
3. 日期选择组件
4. 数据库中船舶静态信息和动态信息应该进行关联
5. 缩放过程中是否进行变换，在这里没有问题，但是如果在地图上，有所放的问题，发达后选择面积会变小
6. 如何高效的索引大量数据，从海量数据中抽取需要的信息
7. 显示所有点的连线问题

## data formate
**ships.dt**
> 132456,3,imono.1,xinghai01,ouka,normal,2,13,gps,2016-7-3 14:45:20,8,lianyu<br>
> 902335,4,imono.1,fuhai02,tuwayae,limite,2,13,gps,2016-7-5 13:23:20,10,shanghai<br>
> 709945,2,imono.1,changji,keylouka,fish,2,13,gps,2016-8-4 10:12:30,9,jinan<br>
> 863590,2,imono.1,yiguo,keylouka,candy,2,13,gps,2015-8-4 10:12:30,9,beijing<br>

**tracks.dt**
> 1,132456,ok,0.2,9.8,50.34,10.23,145,140,2016-10-3 4:23:12,yes,7<br>
> 2,132456,ok,0.2,10.2,10.34,50.23,135,140,2016-11-3 14:23:20,yes,3<br>
> 3,902335,ok,0.2,11.4,33.34,100.23,125,140,2016-8-2 4:23:12,yes,2<br>
> 4,902335,ok,0.2,9.5,35.34,168.23,112,140,2016-9-4 12:23:12,yes,12<br>
> 5,709945,ok,0.2,5.8,70.45,177.23,130,140,2016-10-3 4:23:12,yes,5<br>
> 6,709945,ok,0.2,9.9,38.12,45.23,89,140,2016-11-2 13:23:12,yes,9<br>

## ship information process
##有几个问题需要提醒：
1 项目运行需要Mysql connector，即访问数据库API
2 将Tracks路径点里的messageID类型从Int换成String，否则在解析分析数据的时候会出错，每一步都检验了，但是不知道为什么

### how to orgnazite ship data, include static and dynamic data, how to deal with Concurrent problems.

## the interest is the best teacher
#### go ahead!






