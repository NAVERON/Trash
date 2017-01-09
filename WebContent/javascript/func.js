//自定义的js文件，实现自定义功能
//调用外部文件，更改后需要清除浏览器缓存才能更新  -->解决办法，在文件名上添加时间戳

var mapselect = false;; //选择地图控制flag   true--> baidu  || false-> gaode
var datatab = false;   //打开创建的数据显示窗口
var historytab=false;  //打开历史创建的窗口记录......
var map=null;    //map对象
//var date = new Date();   //还没有用到，可以查看时间等等
var ships = new Array();  //存储ship的数组
var historys = new Array();  //存储历史记录
var syc=null;  //重复函数的返回值，用于停止重复
var ti=true;  //控制重复与否的控制变量，boolean
var markers=new Array();  //存储marker对象的数组

function mapTab(){  //加载页面后就运行
	if(mapselect){  //使用百度地图
		bmapInit();
	}
	else{  //高德地图
		gmapInit();
	}
	if(ti&&!mapselect){  //切换地图会出现这个程序多次运行，即1秒运行两次
		syc=setInterval("tablesych()",1000);  //表格同步刷新,同步让船舶前进
	}
}

function bmapInit(){  //百度地图初始化
	document.getElementsByClassName("list")[0].innerHTML = "BaiduMap";
	map = new BMap.Map("mapshow");          // 创建地图实例  
	var point = new BMap.Point(114.327,30.601);  // 创建点坐标  
	map.centerAndZoom(point, 15);                 // 初始化地图，设置中心点坐标和地图级别  
	map.enableScrollWheelZoom(true);  //滚轮
	
	map.addControl(new BMap.NavigationControl());
	map.addEventListener("click",
		function(e){
			document.getElementById("show").value=e.point.lng + ",   " + e.point.lat;
			
			ms = document.getElementById("makeship");
			ms.style.display="block";
			inner=ms.getElementsByTagName("input");  //inner是所有input对象数组
			inner[0].value="000000";
			inner[1].value="default";
			inner[2].value=Math.round(Number(e.point.lng)*100)/100;
			inner[3].value=Math.round(Number(e.point.lat)*100)/100;
			inner[4].value=0;
			inner[5].value=0;
			inner[6].value="normal";
		}
	);
	//space按键响应
	/*document.onkeydown = function(ev){
		if(ev&&ev.keyCode==32){
			map.disableDragging();
			document.getElementById("tips").innerHTML="拖拽创建船舶";
		}
	}
	document.onkeyup = function(ev) {
		if(ev&&ev.keyCode==32){
			map.enableDragging();
			document.getElementById("tips").innerHTML="已经释放SPACE";
		}
	}*/
}
function gmapInit() {  //高德地图初始化
	document.getElementsByClassName("list")[0].innerHTML = "GaodeMap";  //切换标签显示的文字
	map = new AMap.Map('mapshow', {
        zoom:11,                      //这里是地图的属性设置
        center: [114.327, 30.601],
        resizeEnable: true,
	    doubleClickZoom: false,
	    dragEnable: true,
    });
	map.plugin(["AMap.ToolBar"], function() {  //控件
        map.addControl(new AMap.ToolBar());
    });
	var clickEventListener = map.on('click', function(e) {  //监听点击事件
		document.getElementById("show").value = e.lnglat.getLng() + ',   ' + e.lnglat.getLat();
		
		ms = document.getElementById("makeship");
		ms.style.display="block";
		inner=ms.getElementsByTagName("input");  //inner是所有input对象数组
		inner[0].value="000000";
		inner[1].value="default";
		inner[2].value=Math.round(Number(e.lnglat.getLng())*100)/100;
		inner[3].value=Math.round(Number(e.lnglat.getLat())*100)/100;
		inner[4].value=0;
		inner[5].value=0;
		inner[6].value="normal";
    });
	//设置键盘空格监听
	/*document.onkeydown = function(ev){
		if(ev&&ev.keyCode==32){
			map.setStatus({dragEnable: false});
			document.getElementById("tips").innerHTML="拖拽创建船舶";
		}
	}
	document.onkeyup = function(ev) {
		if(ev&&ev.keyCode==32){
			map.setStatus({dragEnable: true});
			document.getElementById("tips").innerHTML="已经释放SPACE";
		}
	}*/
}

function changeMap(){  //点击切换地图的触发动作
	mapselect = !mapselect;
	map = null;
	mapTab();
}
function timer(){
	if(ti){
		syc=window.clearInterval(syc);
		ti=!ti;
	}
	else{
		syc=self.setInterval("tablesych()",1000);
		ti=!ti;
	}
}
function dataTab(){  //切换到数据显示界面的动作,在下方显示创建的信息，并实时更新
	if(datatab){  //控制div显示与隐藏的两种方法
		/*document.getElementById("showdata").style.visibility="hidden";*/
		document.getElementById("showdata").style.display="none";
		datatab=!datatab;
	}
	else{
		/*document.getElementById("showdata").style.visibility="visible";*/
		document.getElementById("showdata").style.display="block";
		datatab=!datatab;
	}
}

function historyTab(){ //切换到创建历史界面，不显示其他节点
	if(historytab){  //控制div显示与隐藏的两种方法
		/*document.getElementById("showdata").style.visibility="hidden";*/
		document.getElementById("showhistory").style.display="none";
		historytab=!historytab;
	}
	else{
		/*document.getElementById("showdata").style.visibility="visible";*/
		document.getElementById("showhistory").style.display="block";
		historytab=!historytab;
	}
}

/************************************************************************/
//链表
function yes(){
	var info = document.getElementById("makeship").getElementsByTagName("input");
	for(i=0;i<ships.length;i++){          //判断mmsi
		if(info[0].value==ships[i].mmsi){
			alert("重复啦，不能新建");
			return;
		}
	}
	var newship=new createship(info[0].value,info[1].value,Math.round(Number(info[2].value)*100)/100,Math.round(Number(info[3].value)*100)/100,Number(info[4].value),Number(info[5].value),info[6].value);
	ships.push(newship);         //添加船舶对象到ships>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	var newhistory=new historylog(info[0].value,info[1].value,info[2].value,info[3].value,info[4].value,info[5].value,info[6].value);
	historys.push(newhistory);   //添加历史节点>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	//将信息添加到数据更新表和历史创建表中，然后添加到数组中/数据库中
	//得到table节点------------------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>
	//创建点覆盖物，并可以实现变化,点击yes后，在地图上显示点标记
	var mmsi=info[0].value;    //string
	var name=info[1].value;
	var x=Math.round(Number(info[2].value)*100)/100; //number
	var y=Math.round(Number(info[3].value)*100)/100;  //number
	newmarker(mmsi,name,x,y);
	
	/********************************ALERT**************************************************/
	alert("ships number"+ships.length+"::history::"+historys.length);
	var incell=document.getElementById("tb1").insertRow(0);  //incell是tr节点
	//alert(incell.nodeName);  //TR
	
	incell.insertCell(0).innerHTML=info[0].value;
	incell.insertCell(1).innerHTML=info[1].value;
	incell.insertCell(2).innerHTML=info[2].value;// Math.round(Number(info[2].value)*100)/100;
	incell.insertCell(3).innerHTML=info[3].value;// Math.round(Number(info[3].value)*100)/100;
	incell.insertCell(4).innerHTML=info[4].value;
	incell.insertCell(5).innerHTML=info[5].value;
	incell.insertCell(6).innerHTML=info[6].value;
	incell.insertCell(7).innerHTML="<button onclick='deletethis(this)'>delete</button>";
	
	var childs=incell.childNodes;
	for(var i=0;i<childs.length-1;i++){  //添加表格的属性
		childs[i].setAttribute("class","kt");
	}
	
	incell=document.getElementById("tb2").insertRow(0);  //添加历史记录
	incell.insertCell(0).innerHTML=info[0].value;
	incell.insertCell(1).innerHTML=info[1].value;
	incell.insertCell(2).innerHTML=info[2].value;  //Math.round(Number(info[2].value)*100)/100;
	incell.insertCell(3).innerHTML=info[3].value; //Math.round(Number(info[3].value)*100)/100;
	incell.insertCell(4).innerHTML=info[4].value;
	incell.insertCell(5).innerHTML=info[5].value;
	incell.insertCell(6).innerHTML=info[6].value;
	incell.insertCell(7).innerHTML="add";
	
	var childs=incell.childNodes;
	for(var i=0;i<childs.length-1;i++){        //为历史表格添加属性
		childs[i].setAttribute("class","kt");
	}
	document.getElementById("makeship").style.display="none";
}
function cancel(){
	document.getElementById("makeship").style.display="none";
}
function deletethis(obj){
	var i=obj.parentNode.parentNode.rowIndex;  //得到的是tr,根据tr求自己所在的rowindex
	document.getElementById("tb1").deleteRow(i);
	
	var info=obj.parentNode.parentNode.childNodes;  //info是存储的td数组
	var incell=document.getElementById("tb2").insertRow(0);
	incell.insertCell(0).innerHTML=info[0].innerHTML;
	incell.insertCell(1).innerHTML=info[1].innerHTML;
	incell.insertCell(2).innerHTML=info[2].innerHTML;
	incell.insertCell(3).innerHTML=info[3].innerHTML;
	incell.insertCell(4).innerHTML=info[4].innerHTML;
	incell.insertCell(5).innerHTML=info[5].innerHTML;
	incell.insertCell(6).innerHTML=info[6].innerHTML;
	incell.insertCell(7).innerHTML="delete";
	var childs=incell.childNodes;
	for(var i=0;i<childs.length-1;i++){  //设置历史表样式
		childs[i].setAttribute("class","kt");
	}
	//删除记录添加到history数组中
	var newhistory = new historylog(info[0].innerHTML,info[1].innerHTML,info[2].innerHTML,info[3].innerHTML,info[4].innerHTML,info[5].innerHTML,info[6].innerHTML);
	historys.push(newhistory);
	//删除需要标识符才行，mmsi作为唯一标识符
	var identify=incell.childNodes[0].innerHTML;  //得到mmsi
	for(i=0;i<ships.length;i++){         /////////////////////////////////////////////////这里无法删除数组里的对象
		if(ships[i].mmsi==identify){
			var temp=ships.splice(i,1);
		}
	}
	delmarker(identify);
}
/****************************************在这里实现同步功能************************************/
function tablesych(){        //同步刷新表格数据---可以把船舶的前进也写在这里吗？需要！不然会同时操作一个数组
	/*刷新需要刷新3样东西，一个是表格数据，一个是船舶运动参数，还有一个是显示未知的变化*/
	for(var i=0;i<ships.length;i++){
		var ship=ships[i];
		//alert(ship.mmsi+ship.name+ship.x+ship.y+ship.course+ship.speed+ship.type);
		ship.go();
	}
	var t=document.getElementById("tb1").rows;
	if(t.length==0){
		//alert("表示空的，还没有新建对象");
		return;
	}
	else{
		for(var i=0;i<t.length;i++){  //遍历tr
			var tds=t[i].cells;  //得到第i个tr的td序列
			var lua=tds[0].innerHTML;   //得到一行中  的mmsi
			for(var j=0;j<ships.length;j++){
				if(ships[j].mmsi==lua){
					tds[2].innerHTML=ships[j].x;
					tds[3].innerHTML=ships[j].y;
					tds[4].innerHTML=ships[j].course;
					tds[5].innerHTML=ships[j].speed;
				}
			}
		}
		updatemarker();
	}
	
	//document.getElementById("time").innerHTML=ships.length;  //将船舶数量显示在这里
}
function newmarker(mmsi,name,x,y){  //点击鼠标后新建点标记
	this.mmsi=mmsi;
	this.name=name;
	this.x=x;
	this.y=y;
	
	if(!mapselect){
		var lnglat=new AMap.LngLat(x,y);
		var marker=new AMap.Marker({
	        icon: "http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png",
	        position: lnglat,
			animation: "AMAP_ANiMATION_DROP",
			clickable:true,
			tittle: name,
			extData: mmsi
	    });
	    marker.setMap(map);
	    markers.push(marker);  //加入数组
	}
	else{   //百度地图的响应新建   点  --
		//单击添加点，并存储数据库 
		var marker = new BMap.Marker(new BMap.Point(x, y)); // 创建点
		marker.setTitle(mmsi);  //显示唯一标示符
		
		map.addOverlay(marker);
		markers.push(marker);
	}
}
function updatemarker(){  //每隔一次更新一次位置
	for(var i=0;i<markers.length;i++){
		var mmsi=markers[i].getExtData();
		for(var j=0;j<ships.length;j++){
			if(ships[j].mmsi==mmsi){
				markers[i].setPosition(new AMap.LngLat(ships[j].x,ships[j].y));
			}
		}
	}
}
function delmarker(identify){  //删除动作后调用
	this.identify=identify;
	for(var i=0;i<markers.length;i++){
		if(markers[i].getExtData()==identify){
			markers.splice(i,1);
			break;
		}
	}
}
//船舶对象
function createship(mmsi,name,x,y,course,speed,type){  //保存创建对象的结构
	this.mmsi=mmsi;
	this.name=name;
	this.x=x;
	this.y=y;
	this.course=Number(course);
	this.speed=Number(speed)/60;
	this.type=type;  //type直接默认为normal类型
	
	this.go=function(){   //计算过程中有问题1 单位不统一，不在同一量纲下，不能直接计算  2 经纬度到达边界后，又会回到原点
	    this.x += this.speed*Math.sin(Math.PI/180*this.course);
		this.y += this.speed*Math.cos(Math.PI/180*this.course);
		this.x=Math.round(this.x*100)/100;
		this.y=Math.round(this.y*100)/100;
		/*this.x.toFixed(2);
		this.y.toFixed(2);*/
	}
}
function historylog(mmsi,name,x,y,course,speed,type){  //保存历史记录的结构
	this.mmsi=mmsi;
	this.name=name;
	this.x=Math.round(Number(x)*100)/100;
	this.y=Math.round(Number(y)*100)/100;
	this.course=Number(course);
	this.speed=Number(speed)/3600;
	this.type=type;
}

function storeSQL(){
	//保存数据到SQL数据库中
	var db = openDatabase('mydb', '1.0', 'Test DB', 2 * 1024 * 1024);
	if(!db){
		alert("database not exist!");
	}
	db.transaction(function (tx) {
	  tx.executeSql("CREATE TABLE IF NOT EXISTS SHIPS (mmsi varchar(10) unique, name varchar(10), x Double, y Double, course Double, speed Double, type varchar(10))");
	  tx.executeSql("CREATE TABLE IF NOT EXISTS HISTORYS (mmsi varchar(10) unique, name varchar(10), x Double, y Double, course Double, speed Double, type varchar(10))");
	  for(var i=0;i<ships.lenght;i++){
		  tx.executeSql("INSERT INTO SHIPS (mmsi,name,x,y,course,speed,type) VALUES (?, ?,?,?,?,?,?)", [ships[i].mmsi, ships[i].name,ships[i].x,ships[i].y, ships[i].course,ships[i].speed, ships[i].type]);
	  }
	  for(var j=0;j<historys.length;j++){
		  tx.executeSql("INSERT INTO HISTORYS (mmsi,name,x,y,course,speed,type) VALUES (?, ?,?,?,?,?,?)", [ships[j].mmsi, ships[j].name,ships[j].x,ships[j].y, ships[j].course,ships[j].speed, ships[j].type]);
	  }
	  
	});
	/*db.transaction(function(tx){
		tx.executeSql("SELECT * FROM SHIPS", [], function (tx, results) {
            var len = results.rows.length, i;
            msg = "<p>查询记录条数: " + len + "</p>";
            document.getElementById("result").innerHTML +=  msg;	
            for (i = 0; i < len; i++){
               msg = "<p><b>" + results.rows.item(i).name + "</b></p>";
               document.getElementById("result").innerHTML +=  msg;
            }
         }, null);
		}
	);*/
}
function deleteSQL(mmsi){
	this.mmsi=mmsi;
	var db = openDatabase('mydb', '1.0', 'Test DB', 2 * 1024 * 1024);
	if(!db){
		alert("database not exist!");
	}
	db.transaction(function (tx) {
		tx.executeSql("DELETE FROM SHIPS WHERE mmsi=mmsi");
	});
}






