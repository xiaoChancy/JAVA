@extends('header.admin')

@section('title')
    <title>清理积分</title>
@endsection

@section('content')
    <script type="text/javascript">
        function clean() {
            document.location.href = "{{url('admin/timing_clean_integral/clean/true')}}";
        }
    </script>
    <div class="container">
        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        清理积分
                    </div>
                    <div class="panel-body">
                        <table class="table table-bordered table-hover table-striped" id="tb_data">
                            <thead>
                                <tr>
                                    <td><button type="button" onclick="clean()" class="btn btn-default btn-sm">清空积分</button></td>
                                    <td>备注：（用户数据库）非管理员不可操作</td>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
@endsection