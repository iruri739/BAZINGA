package com.accenture.huaweigroup.controller;

import com.accenture.huaweigroup.module.entity.*;
import com.accenture.huaweigroup.module.bean.BattleData;
import com.accenture.huaweigroup.service.ChessService;

import com.accenture.huaweigroup.service.GameService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin
@RequestMapping("/game")
@Api(value = "游戏逻辑", tags = "游戏逻辑接口")
public class GameController {

    private static final Logger LOG = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private GameService gameService;

    @ApiOperation(value = "玩家匹配", notes = "玩家调用接口，返回true表明匹配成功，false表明仍在匹配", httpMethod = "GET")
    @GetMapping("/matchGame")
    public boolean matchGame(@RequestParam("playerId") int playerId) {
        try {
            return gameService.matchGame(playerId);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("玩家[" + playerId +"] 匹配过程发生错误！！！");
        }
        return false;
    }

    @ApiOperation(value = "断线重连检测", notes = "检查用户是否已经在游戏中，如果是则返回true，否则返回false", httpMethod = "GET")
    @GetMapping("/checkGameState")
    public boolean disconnectCheck(@RequestParam("userId") int userId) {
        return gameService.checkGameState(userId);
    }

    @ApiOperation(value = "获取初始化数据接口", notes = "通过调用该接口获得初始json数据对象", httpMethod = "GET")
    @GetMapping("/defaultDataModel")
    public BattleData sendDefaultDataModel(@RequestParam("playerId") int playerId) {
        return gameService.getInitGameData(playerId);
    }

    @ApiOperation(value = "进入准备阶段检测", notes = "检测玩家是否准备开启战斗", httpMethod = "GET")
    @GetMapping("/gamePrepareCheck")
    public String gameCircleCheck(@RequestParam("gameId") String gameId,@RequestParam("playerId") int playerId) {
        return gameService.gameGoPrepareCheck(gameId, playerId);
    }

    @ApiOperation(value = "进入战斗阶段检测", notes = "检测玩家是否可以进入战斗阶段", httpMethod = "GET")
    @GetMapping("/gameBattleCheck")
    public boolean gameBattleCheck(@RequestParam("gameId") String gameId, @RequestParam("playerId") int playerId) {
        return gameService.gameBattleCheck(gameId, playerId);
    }

    @ApiOperation(value = "获取刷新待选区卡牌列表", notes = "扣除玩家2金币刷新待选区卡牌，无法刷新则返回null", httpMethod = "GET")
    @GetMapping(value = "/getChessData")
    public ArrayList<Chess> getChessData(@RequestParam("gameId") String gameId,
                                         @RequestParam("playerId") int playerId)
    {
        return gameService.changePlayerInventory(gameId, playerId);
    }

    @ApiOperation(value = "战场数据传输接口", notes = "向服务器发送json对象，返回服务器最新状态的json对象", httpMethod = "POST")
    @PostMapping("/battleDataApi")
    public BattleData sendBattleData(@RequestParam("gameId") String gameId,
                                     @RequestParam("playerId") int playerId,
                                     @RequestBody BattleData data) {
        return gameService.battleDataApi(gameId , playerId, data);
    }


//    @GetMapping("/checkGameBattle")
//    public boolean checkGameBattle(@RequestParam("playerId") int playerId) {
//        return false;
//    }


//    @ApiOperation(value = "验证玩家匹配", notes = "验证匹配的玩家是否都已经准备开始游戏", httpMethod = "GET")
//    @GetMapping("/checkMatch")
//    public boolean checkMatch(@RequestParam("playerId") int playerId) {
//        if (gameService.matchReadyCheck(playerId)) {
////            Game game = gameService.findGameByPlayerId(playerId);
//            return true;
//        }
//        return false;
//    }

//    @ApiOperation(value = "取消首次匹配", notes = "取消玩家匹配过程接口", httpMethod = "GET")
//    @GetMapping("/cancelMatch")
//    public boolean cancelMatch(@RequestParam("playerId") int playerId) {
//        try {
//            if (gameService.cancelMatch(playerId)) {
//                LOG.error("玩家[" + playerId +"] 取消匹配过程成功！");
//                return true;
//            } else {
//                LOG.error("玩家[" + playerId +"] 取消匹配过程失败！该玩家未开始匹配或已经进入游戏！");
//                return false;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOG.error("玩家[" + playerId +"] 取消匹配过程发生错误！！！");
//        }
//        return false;
//    }

//    @ApiOperation(value = "获取初始游戏数据", notes = "调用接口获取初始玩家游戏数据", httpMethod = "GET")
//    @GetMapping("/getInitData")
//    public BattleData getGameInitData(@RequestParam("playerId") int playerId) {
//        return gameService.getInitGameData(playerId);
//    }

	
}
