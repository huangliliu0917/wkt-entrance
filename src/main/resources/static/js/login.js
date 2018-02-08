
    //记住我
    $('#rememberme').on('click', function(){
//		console.log($('#rememberme').is(':checked'))
        var remember_me = $('#rememberme').is(':checked');
        localStorage.setItem("remember_me", remember_me);
    })
    var remember_storage = localStorage.getItem('remember_me');
    if(remember_storage === 'true'){
        $("#rememberme").prop("checked",true);
    }else{
        $("#rememberme").prop("checked",false);
    }

    $("#logincheck").click(function(){
        $.ajax({
            type: "POST",
            url: "/login",
            data: "",
            success: function(data){
                $('#login-req').html(data)
                if(data==null){
                    return false
                }else{
                    alert(data)
                }
            }
        });

    })
