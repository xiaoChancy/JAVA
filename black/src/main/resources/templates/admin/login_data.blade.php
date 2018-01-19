@extends('header.admin')

@section('title')
    <title>登录数据</title>
@endsection

@section('content')
    <script type="text/javascript">
        function get_data() {
            var date = document.getElementById('date').value;
            var date2 = document.getElementById('date2').value;
            document.location.href = "{{url('admin/login_data/date')}}"+'/'+ date +'/date2/'+ date2;
        }
    </script>
        <div class="row" style="padding-left: 40px;padding-right: 40px">
            <div class="panel panel-default">
                <div class="panel-heading">
                    活动登录人数数据
                </div>
                <div class="panel-body">
                    <label for="date">日期:</label>
                    <input id="date" name="date" type="date"
                           @if(isset($date_stamp))
                           value="{{$date_stamp}}"
                           @endif
                    />
                    <input id="date2" name="date" type="date"
                           @if(isset($date_stamp2))
                           value="{{$date_stamp2}}"
                           @endif
                    />
                    <button class="btn" onclick="get_data();">获取数据</button>
                    <br/><br/>
                    <div style="float: left">
                        <table class="table table-bordered table-hover table-striped">
                            <thead>
                                <th>河北麻将</th>
                            </thead>
                            <tbody>
                                <td>{{$hebeimj}}</td>
                            </tbody>
                        </table>
                    </div>
                    <div style="float: left">
                        <table class="table table-bordered table-hover table-striped">
                            <thead>
                                <th>国际麻将</th>
                            </thead>
                            <tbody>
                                <td>{{$gbmj}}</td>
                            </tbody>
                        </table>
                    </div>
                    <div style="float: left">
                        <table class="table table-bordered table-hover table-striped">
                            <thead>
                                <th>血战到底</th>
                            </thead>
                            <tbody>
                                <td>{{$mahjong}}</td>
                            </tbody>
                        </table>
                    </div>
                    <div style="float: left">
                        <table class="table table-bordered table-hover table-striped">
                            <thead>
                                <th>斗地主</th>
                            </thead>
                            <tbody>
                                    <td>{{$dou_di_zhu}}</td>
                            </tbody>
                        </table>
                    </div>
                    <div style="float: left">
                        <table class="table table-bordered table-hover table-striped">
                            <thead>
                                <th>飞行棋</th>
                            </thead>
                            <tbody>
                                <td>{{$ludo}}</td>
                            </tbody>
                        </table>
                    </div>
                    <div style="float: left">
                        <table class="table table-bordered table-hover table-striped">
                            <thead>
                                <th>斗兽棋</th>
                            </thead>
                            <tbody>
                                <td>{{$jungle}}</td>
                            </tbody>
                        </table>
                    </div>
                    <div style="float: left">
                        <table class="table table-bordered table-hover table-striped">
                            <thead>
                                <th>五子棋</th>
                            </thead>
                            <tbody>
                                <td>{{$gobang}}</td>
                            </tbody>
                        </table>
                    </div>
                    <div style="float: left">
                        <table class="table table-bordered table-hover table-striped"  >
                            <thead>
                                <th>黑白棋</th>
                            </thead>
                            <tbody>
                                <td>{{$blackwhite}}</td>
                            </tbody>
                        </table>
                     </div>
                    <div style="float: left">
                        <table class="table table-bordered table-hover table-striped"  >
                            <thead>
                            <th>总计</th>
                            </thead>
                            <tbody>
                            <td>{{$sum}}</td>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
@endsection