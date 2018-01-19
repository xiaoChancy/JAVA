@extends('header.admin')

@section('title')
    <title>文件上传</title>
@endsection

@section('content')
    <div class="container">
        <div class="row">
            <div class="col-md-10 col-md-offset-1">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        文件上传
                    </div>
                    <div class="panel-body">
                        <form action="{{url('admin/go_upload')}}" method="post" enctype="multipart/form-data">
                            <label for="file">文件名：</label>
                            <input type="file" name="file" id="file"><br>
                            <input type="submit" name="submit" value="提交">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
@endsection