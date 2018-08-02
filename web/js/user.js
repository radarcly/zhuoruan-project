// $(document).ready(function () {
//     console.log("Ready");
// })

$('.form').find('input, textarea').on('keyup blur focus', function (e) {
    console.log("event happen");
    var $this = $(this),
        label = $this.prev('label');
    if (e.type === 'keyup') {
        if ($this.val() === '') {
            label.removeClass('active highlight');
        } else {
            label.addClass('active highlight');
        }
    } else if (e.type === 'blur') {
        if( $this.val() === '' ) {
            label.removeClass('active highlight');
        } else {
            label.removeClass('highlight');
        }
    } else if (e.type === 'focus') {
        if( $this.val() === '' ) {
            label.removeClass('highlight');
        }
        else if( $this.val() !== '' ) {
            label.addClass('highlight');
        }
    }
});

$('.tab a').on('click', function (e) {
    e.preventDefault();
    $(this).parent().addClass('active');
    $(this).parent().siblings().removeClass('active');
    target = $(this).attr('href');
    $('.tab-content > div').not(target).hide();
    $(target).fadeIn(600);
});

$('#registerButton').on('click',function (e) {
    console.log("button clicked");
    inputName = $(" #registerName ").val();
    inputPassword = $(" #registerPassword").val();
    console.log("name: "+ inputName+ " password: " + inputPassword );
    $.post("RegisterServlet",{name:inputName,password:inputPassword},function(string){
        $.trim(string);
        console.log("retrunValue: " + string);
        if(string == "-1"){
            console.log("用户名已存在");
        }else if(string == "1" ){
            console.log("注册成功");
        }else{
            console.log("other");
        }
    });
});

$('#loginButton').on('click',function (e) {
    console.log("button clicked");
    inputName = $(" #loginName ").val();
    inputPassword = $(" #loginPassword ").val();
    console.log("name: "+ inputName+ " password: " + inputPassword );
    $.post("LoginServlet",{name:inputName,password:inputPassword},function(string){
        $.trim(string);
        console.log("retrunValue: " + string);
        if(string == "-1" ){
            console.log("密码错误");
        }else if(string == "1" ){
            console.log("登录成功");
        }else if(string == "-2" ){
            console.log("用户不存在")
        }
    });
});