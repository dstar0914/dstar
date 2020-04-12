var main = {
    init : function () {
        var _this = this;
        $('.submit_btn[data-type=save]').on('click', function () {
            _this.save();
        });
    },
    save : function () {
        var data = {
            url: $('[name=url]').val(),
            data: $('[name=data]').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/scrap',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();