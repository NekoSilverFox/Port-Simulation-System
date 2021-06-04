<%--
  Created by IntelliJ IDEA.
  User: mi
  Date: 2021/4/26
  Time: 13:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Result</title>
</head>

<body>
    <p style="text-align: left;"><strong>GitHub：</strong>NekoSilverfox</p>
    <p style="text-align: left;"><strong>ИФО：</strong>Мэн Цзянин</p>
    <p style="text-align: left;"><strong>Группа：</strong>3530904/90002</p>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" id="u0"
              href="https://zh.rakko.tools/tools/129/lib/tinymce/skins/ui/oxide/content.min.css">
        <link rel="stylesheet" type="text/css" id="u1"
              href="https://zh.rakko.tools/tools/129/lib/tinymce/skins/content/default/content.min.css">
    </head>
    <body id="tinymce" class="mce-content-body " data-id="content" contenteditable="true" spellcheck="false">
        <p>
            <span style="color: rgb(224, 62, 45);" data-mce-style="color: #e03e2d;">
            <strong> DATA SOURCE DATA >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>></strong>
            </span>
        </p>
    </body>

    ${msg}

    <br/>
    <br/>
    <body id="tinymce" class="mce-content-body " data-id="content" contenteditable="true" spellcheck="false">
        <p>
                <span style="color: rgb(45, 194, 107);" data-mce-style="color: #e03e2d;">
                <strong> FORMATTED DATA >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>></strong>
                </span>
        </p>
    </body>

    <p id="p1" style="font-weight:bold">Simulation results of container ships:</p>
    <p id="ship1" style="white-space: pre;"></p>
    <br/>

    <p id="p2" style="font-weight:bold">Simulation results of bulk carriers:</p>
    <p id="ship2" style="white-space: pre;"></p>
    <br/>

    <p id="p3" style="font-weight:bold">Simulation results of the liquid ship:</p>
    <p id="ship3" style="white-space: pre;"></p>
    <br/>

    <p id="p4" style="font-weight:bold">Combined simulation results:</p>
    <p id="ship4" style="white-space: pre;"></p>
</body>

<script>
    var date = ${msg}
    var ms2min = 1000 * 60;
    var S1 = document.getElementById("ship1");
    var S2 = document.getElementById("ship2");
    var S3 = document.getElementById("ship3");
    var S4 = document.getElementById("ship4");
    for (var i in date) {
        var numC = "     ▶  Min cranes:  " + date[i].numCrane;
        var numF = "    ▶  Number freighters:  " + date[i].numFreighters;
        var aveT = "    ▶  Average waiting time in queue:  " + parseInt(date[i].averageTimeOfUnloading / ms2min) + " min";
        var aveU = "    ▶  Max unloading delay time:  " + parseInt(date[i].averageUnloadingDelayTime / ms2min) + " min";
        var aveW = "    ▶  Average unloading delay time:  " + parseInt(date[i].averageWaitingTimeInQueue / ms2min) + " min";
        var maxU = "    ▶  Average time of unloading:  " + parseInt(date[i].maxUnloadingDelayTime / ms2min) + " min";
        var totF = "    ▶  Total fine:  " + parseInt(date[i].totalFine);
        var resultMsg =
              numC + " \n "
            + numF + " \n "
            + aveT + " \n "
            + aveU + " \n "
            + aveW + " \n "
            + maxU + " \n "
            + totF;
        var M = document.createTextNode(resultMsg);

        // 提示
        alert(resultMsg);

        if (i == 0) {
            S1.appendChild(M);
        } else if (i == 1) {
            S2.appendChild(M);
        } else if (i == 2) {
            S3.appendChild(M);
        } else {
            S4.appendChild(M);
        }
    }
</script>
</html>
