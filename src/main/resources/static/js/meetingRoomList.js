// time table 클릭이벤트

// $(function() {
//     $("#listTable #tbodyId").on("mousedown","td",function(){
//         alert( $(this).find("td:eq(0)").text() );
//         alert( $(this).find("td:eq(1)").text() );
//
//
//     });
// });

var dragging = false;
var dragSelectIds = [];
var $td = $('td');
var startCell = null;

function end(e) {
    dragSelectIds = [];

    $td.removeClass('selected-item');

    $(cellsBetween(startCell, e.target)).each(function() {
        $(this).addClass('selected-item');
        dragSelectIds.push($(this).attr('id'));
    });
}

function cellsBetween(start, end) {
    var bounds, elementsInside;
    elementsInside = [start, end];
    do {
        bounds = getBoundsForElements(elementsInside);
        var elementsInsideAfterExpansion = rectangleSelect('td', bounds);
        if (elementsInside.length === elementsInsideAfterExpansion.length) {
            return elementsInside;
        } else {
            elementsInside = elementsInsideAfterExpansion;
        }
    } while (true);
}

function isPointBetween(point, x1, x2) {
    return (point >= x1 && point <= x2) || (point <= x1 && point >= x2);
}

function rectangleSelect(selector, bounds) {
    var elements = [];
    jQuery(selector).each(function() {
        var $this = jQuery(this);
        var offset = $this.offset();
        var x = offset.left;
        var y = offset.top;
        var w = $this.outerWidth();
        var h = $this.outerHeight();
        if ((isPointBetween(x + 1, bounds.minX, bounds.maxX) && isPointBetween(y + 1, bounds.minY, bounds.maxY)) ||
            (isPointBetween(x + w - 1, bounds.minX, bounds.maxX) && isPointBetween(y + h - 1, bounds.minY, bounds.maxY))
        ) {
            elements.push($this.get(0));
        }
    });
    return elements;
}

function getBoundsForElements(elements) {
    var x1 = elements.reduce(function(currMinX, element) {
        var elementLeft = $(element).offset().left;
        return currMinX && currMinX < elementLeft ? currMinX : elementLeft;
    }, undefined);
    var x2 = elements.reduce(function(currMaxX, element) {
        var elementRight = $(element).offset().left + $(element).outerWidth();
        return currMaxX && currMaxX > elementRight ? currMaxX : elementRight;
    }, undefined);
    var y1 = elements.reduce(function(currMinY, element) {
        var elementTop = $(element).offset().top;
        return currMinY && currMinY < elementTop ? currMinY : elementTop;
    }, undefined);
    var y2 = elements.reduce(function(currMaxY, element) {
        var elementBottom = $(element).offset().top + $(element).outerHeight();
        return currMaxY && currMaxY > elementBottom ? currMaxY : elementBottom;
    }, undefined);
    return {
        minX: x1,
        maxX: x2,
        minY: y1,
        maxY: y2
    };
}

$td.on('mousedown', function(e) {
    dragging = true;
    startCell = e.target;
    end(e);
});
$td.on('mouseenter', function(e) {
    if (!dragging) {
        return;
    }
    end(e);
});
$td.on('mouseup', function(e) {
    dragging = false;
    console.log(dragSelectIds);
    document.getElementById('result').innerHTML = dragSelectIds;
});


// td로 th값 가져오기

// 버튼 클릭시 Row 값 가져오기
$(".checkBtn").click(function(){

    var str = ""
    var tdArr = new Array();	// 배열 선언
    var checkBtn = $(this);

    // checkBtn.parent() : checkBtn의 부모는 <td>이다.
    // checkBtn.parent().parent() : <td>의 부모이므로 <tr>이다.
    var tr = checkBtn.parent().parent();
    var td = tr.children();

    console.log("클릭한 Row의 모든 데이터 : "+tr.text());

    var no = td.eq(0).text();
    var userid = td.eq(1).text();
    var name = td.eq(2).text();
    var email = td.eq(3).text();


    // 반복문을 이용해서 배열에 값을 담아 사용할 수 도 있다.
    td.each(function(i){
        tdArr.push(td.eq(i).text());
    });


    $("#ex2_Result1").html(" * 클릭한 Row의 모든 데이터 = " + tr.text());

});




// 오늘 이후로 시간 예약하지 못하게
var now_utc = Date.now()
var timeOff = new Date().getTimezoneOffset()*60000;
var today = new Date(now_utc-timeOff).toISOString().split("T")[0];
document.getElementById("reserveDate").setAttribute("min", today);
