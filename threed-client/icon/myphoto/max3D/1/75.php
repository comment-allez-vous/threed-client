<?php
require('../../class/connect.php'); //引入数据库配置文件和公共函数文件
require('../../class/db_sql.php'); //引入数据库操作文件
require('../../class/userfun.php'); //引入数据库操作文件
$link=db_connect(); //连接MYSQL
$empire=new mysqlquery(); //声明数据库操作类
$editor=1; //声明目录层次


$where=" where 1=1  ";

$keyword=$_GET[keyword];

$arr=array("newstime"=>"时间排序","price"=>"价格排序","psalenum"=>"销量排序");

$mod=$_GET[myorder]?'&myorder=0':'&myorder=1';
$mod.=$keyword?"&keyword=".$keyword:'';
foreach ($arr as $key => $value) {

  $pxlist.='<li class="sortbtn_item  js_listitem fieldandcss '.($key==$_GET[orderby]?'selected':'').' " ><a href="/e/extend/search/?orderby='.$key.$mod.'" class="link">'.$value.'</a></li>';

}

$where.=$keyword ? " and title LIKE '%".$keyword."%' ":'';
$orderby=$_GET[orderby]?"order by ".$_GET[orderby]:'';
if($orderby){

$px=$_GET[myorder]?" desc":" asc";

}
$where.=$orderby.$px;
$sql="SELECT title,titlepic,titleurl,price FROM {$dbtbpre}ecms_shop ".$where." LIMIT 20 ";
$res=mysql_query($sql);
?>
<!DOCTYPE html>
<html>
  
  <head>
    <meta http-equiv="Content-Type" content="text/html;
    charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="Cache-Control" content="no-cache,no-store,must-revalidate">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <meta name="x5-page-mode" content="no-title">
    <meta name="blink-page-mode" content="no-title">
    <title>搜索</title>
    <meta name="keywords" content="搜索">
    <meta name="description" content="搜索">
    <link rel="shortcut icon" href="/favicon.ico">
    <link rel="stylesheet" type="text/css" href="/skin/yanjing/css/common.css">
    <link rel="stylesheet" type="text/css" href="/skin/yanjing/css/swiper.css">
    <link rel="stylesheet" type="text/css" href="/skin/yanjing/css/classmenu_classfilter.css">
    <script type="text/javascript" src="/skin/yanjing/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="/skin/yanjing/js/swiper.js"></script>
    <script type="text/javascript" src="/skin/layer/mobile/layer.js"></script>

  </head>
  <body style="">
    <div class="viewport pb-160">
      <header class="header">
		    <div class="header_mask"></div>
		    <div class="hd_btn_left">
		        <a href="javascript:if(window.history.length>1){history.go(-1);}else{location.href = '/';}" class="hd_btn hd_btn_return head_icon"></a>
		    </div>
		    <div class="hd_search head_icon">
          <form action="" method="GET">
		       <input id="searchText" class="search_text"   value="" name="keyword" type="search" placeholder="请输入您要搜索的物品">
          </form>
		    </div>
		  </header>

        <div data-cid="894fb563-fcf0-4bd9-b304-eed7f37eec8e" id="js_framelist_v">
          <div class="sort_toolbar up_tool" id="js_searchlist">
            <ul>
              <?=$pxlist?> 
            </ul>
          </div>
          <div class="goodlistview">
            <ul class="goodsshowItem" id="js_goodslist">

            <?php
            while($bqr=mysql_fetch_assoc($res))
            {
            ?>
              <li>
                <a href="<?=$bqr[titleurl]?>" onclick="">
                  <div class="s_goodsinfo">
                    <img src="<?=$bqr[titlepic]?>" role="<?=$bqr[title]?>" title=" <?=$bqr[title]?>" class="pro-img">
                    <h4><?=$bqr[title]?></h4>
                    <b>￥<?=$bqr[price]?></b>
                  </div>
                </a>
              </li>
            <?php
            }
            ?> 
            </ul>
            <div id="prompt_title" class="goodslist_msg " data-totalpage="29">
              <p class="goodsnull_text" page="1">点击加载更多</p>
              <p class="text-c noload_text">没有更多了</p>
              <p class="load_msg">
                <img src="/skin/yanjing/img/img_loding.gif" width="16px" class="loading_img">正在加载…
              </p>
            </div>
          </div>
        </div>
        <footer class="footer_menu">
          <ul>
            <li class="foot_item ">
              <a href="/" class="link_home">首页</a></li>
            <li class="foot_item ">
              <a href="/e/extend/fenlei/" class="link_prdtype">分类</a></li>
            <li class="foot_item ">
              <a href="/e/ShopSys/buycar/" class="link_cart">购物车</a>
              <i class="msg_count J_cartCount"><script src="/e/ShopSys/buycar/buycarjs.php"></script></i></li>
            <li class="foot_item ">
              <a href="/e/member/" class="link_account">我的</a></li>
          </ul>
        </footer>
      </div>
    </div>
    <script type="text/javascript">
          //设置当前页数
        $(".goodsnull_text").on('click',function  () {

               var i =$(this).attr("page"); 
                $.ajax({
                    url : '/e/extend/search/getmore.php',
                    type:'POST',
                    data:{"next":i,'table':'shop','limit':20,'small_length':120,"orderby":'<?=$_GET[orderby]?>',"myorder":'<?=$_GET[myorder]?>',"keyword":'<?=$_GET[keyword]?>'},
                    dataType : 'html',
                    beforeSend:function(){
                    $(".load_msg").show();
                    $('.goodsnull_text').hide();
                    },
                    success : function(data){
                    if(data){
                    $("#js_goodslist").append(data);
                    $(".load_msg").hide();
                      i=parseInt(i)+1;
                    $('.goodsnull_text').show().attr("page",i);
                    
                    }else{
                    $(".load_msg").hide();
                    $('.noload_text').show()
                    return false;
                    }
                    }
                });
        })
    </script>

  </body>

</html>
<?php 

db_close(); //关闭MYSQL链接
$empire=null; //注消操作类变量
?>