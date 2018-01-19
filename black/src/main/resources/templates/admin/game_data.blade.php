@extends('header.admin')

@section('title')
    <title>积分数据</title>
@endsection

@section('content')
    <script type="text/javascript">
        function get_data() {
            var user_id = document.getElementById('user_id').value;
            var date = document.getElementById('date').value;
            document.location.href = "{{url('admin/game_data/user_id')}}"+'/'+ user_id + '/date/' + date;
        }
    </script>
    <div class="container">
        <div class="row">
            <div class="col-md-10 col-md-offset-1">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        积分数据
                    </div>
                    <div class="panel-body">
                        <label for="date">日期:</label>
                        <input id="date" name="date" type="date"
                               @if(isset($date_stamp))
                               value="{{$date_stamp}}"
                               @endif
                        />
                        <label for="date">用户ID:</label>
                        <input id="user_id" name="user_id" type="text"
                               @if(isset($user_id))
                               value="{{$user_id}}"
                               @endif
                        />
                        <button class="btn" onclick="get_data();">获取数据</button>
                        <div class="panel-body">
                            <table class="table table-bordered table-hover table-striped" id="tb_data">
                                <thead>
                                <tr>
                                    <td>序号</td>
                                    <td>时间</td>
                                    <td>用户Id</td>
                                    <td>游戏名</td>
                                    <td>积分</td>
                                </tr>
                                </thead>
                                <tbody>
                                @if($game_datas)
                                @foreach ($game_datas as $k => $value)
                                <tr>
                                    <td>{{$k+1}}</td>
                                    <td>{{$value['date_stamp']}}</td>
                                    <td>{{$value['user_id']}}</td>
                                    <td>
                                        @if($value['game_name'] == 'hebeimj')
                                            河北麻将
                                        @elseif($value['game_name'] == 'mahjong')
                                            血战到底
                                        @elseif($value['game_name'] == 'gbmj')
                                            国标麻将
                                        @elseif($value['game_name'] == 'dou_di_zhu')
                                            斗地主
                                        @elseif($value['game_name'] == 'ludo')
                                            飞行棋
                                        @elseif($value['game_name'] == 'gobang')
                                            五子棋
                                        @elseif($value['game_name'] == 'blackwhite')
                                            黑白棋
                                        @elseif($value['game_name'] == 'jungle')
                                            斗兽棋
                                        @endif
                                    </td>
                                    <td>{{$value['integral']}}</td>
                                </tr>
                                @endforeach
                                @endif
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
@endsection