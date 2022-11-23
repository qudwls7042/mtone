$('#email').on('keyup' ,function(event) {
    if (!/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/g.test($('#email').val())) {
        $('#invalid-email').html("<i class='bi bi-exclamation-circle'></i> 유효한 이메일 주소를 입력해 주시기 바랍니다.");
        $('#invalid-email').show(); // show() 메소드 추가
    } else {
        $('#invalid-email').empty();
    }
});