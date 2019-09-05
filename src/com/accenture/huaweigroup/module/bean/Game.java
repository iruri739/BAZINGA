package com.accenture.huaweigroup.module.bean;

import com.accenture.huaweigroup.module.entity.Chess;
import com.accenture.huaweigroup.module.bean.GameState;
import com.accenture.huaweigroup.service.ChessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class Game {
    private static final int PLAYER_DEFAULT_PREPARETIME = 30;
    private static final int BATTLE_DEFAULT_TIME = 60;
    private static final ScheduledExecutorService scheduleService = Executors.newSingleThreadScheduledExecutor();

    private int id;
    private int totalTime = 0;
    private int rounds = 1;
    private int prepareTime = PLAYER_DEFAULT_PREPARETIME;
    private boolean startPrepare = false;
    private int battleTime = BATTLE_DEFAULT_TIME;
    private boolean startBattle = false;
    private GameState state = GameState.CREATED;
    private Player playerOne = new Player();
    private Player playerTwo = new Player();

    private Runnable checkTimeState = new Runnable() {
        @Override
        public void run() {
            if (checkPlayerState(PlayerState.PREPARE)) {
                startPrepare = true;
            }
            if (checkPlayerState(PlayerState.BATTLE)) {
                startBattle = true;
            }
        }
    };
    private Runnable prepareRun = new Runnable() {
        @Override
        public void run() {
            if (startPrepare) {
                prepareTime--;
                System.out.println("剩余准备时间：" + prepareTime);
                if (prepareTime == 0) {
                    startPrepare = false;
                    prepareTime = PLAYER_DEFAULT_PREPARETIME;
                }
            }
        }
    };

    private Runnable battleRun = new Runnable() {
        @Override
        public void run() {
            if (startBattle) {
                battleTime--;
                System.out.println("剩余战斗时间：" + battleTime);
                if (battleTime == 0) {
                    startBattle = false;
                    battleTime = BATTLE_DEFAULT_TIME;
                }
            }
        }
    };

    public Game() {
        super();
    }

    public Game(int id, int playerOneId, int playerTwoId) {
        super();
        this.id = id;
        this.playerOne.setId(playerOneId);
        this.playerTwo.setId(playerTwoId);
        scheduleService.scheduleAtFixedRate(checkTimeState, 2, 1, TimeUnit.SECONDS);
        scheduleService.scheduleAtFixedRate(prepareRun, 2, 1, TimeUnit.SECONDS);
        scheduleService.scheduleAtFixedRate(battleRun, 2, 1, TimeUnit.SECONDS);

    }

    public boolean refreshData(BattleData data) {
        if (this.id == data.getGameId()) {
            this.playerOne = data.getPlayerOneData();
            this.playerTwo = data.getPlayerTwoData();
            return true;
        }
        return false;
    }

    public boolean containPlayer(int playerId) {
        if (playerOne.getId() == playerId || playerTwo.getId() == playerId) {
            return true;
        }
        return false;
    }

    public boolean checkPlayerState(PlayerState state) {
        if (playerOne.getState() == state && playerTwo.getState() == state) {
            return true;
        }
        return false;
    }

    public void resetTime() {
        this.prepareTime = PLAYER_DEFAULT_PREPARETIME;
        this.playerOne.setState(PlayerState.PREPARE);
        this.playerTwo.setState(PlayerState.PREPARE);
    }

    public Player getPlayer(int playerId) {
        if (playerOne.getId() == playerId) {
            return playerOne;
        } else {
            return playerTwo;
        }
    }

    public Player getConponentPlayer(int playerId) {
        if (playerOne.getId() != playerId) {
            return playerOne;
        } else {
            return playerTwo;
        }
    }

    //双方玩家战场上卡牌的战斗处理
    public int fight() {
        return 0;
    }

    //花费金币更换玩家待选区卡牌
    public boolean randInventory(int userId) {
        return false;
    }

    //获取玩家手牌
    public ArrayList<Chess> getPlayerHandCards(int playerId) {
        Player player = getPlayer(playerId);
        return player.getHandCards();
    }

    //获取玩家战场卡牌
    public ArrayList<Chess> getPlayerBattleCards(int playerId) {
        Player player = getPlayer(playerId);
        return player.getBattleCards();
    }

    //获取玩家待选区卡牌
    public ArrayList<Chess> getPlayerCardInventory(int playerId) {
        Player player = getPlayer(playerId);
        return player.getCardInventory();
    }

    //更新玩家手牌
    public ArrayList<Chess> setPlayerHandCards(int playerId, ArrayList<Chess> newHandCards) {
        Player player = getPlayer(playerId);
        player.setHandCards(newHandCards);
        return player.getHandCards();
    }

    //更新玩家战场卡牌
    public ArrayList<Chess> setPlayerBattleCards(int playerId, ArrayList<Chess> newBattleCards) {
        Player player = getPlayer(playerId);
        player.setBattleCards(newBattleCards);
        return player.getBattleCards();
    }

    //更新玩家待选区卡牌
    public ArrayList<Chess> setPlayerCardInventory(int playerId, ArrayList<Chess> newInventory) {
        Player player = getPlayer(playerId);
        player.setCardInventory(newInventory);
        return player.getCardInventory();
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrepareTime() {
        return prepareTime;
    }

    public void setPrepareTime(int prepareTime) {
        this.prepareTime = prepareTime;
    }

    public boolean isStartPrepare() {
        return startPrepare;
    }

    public void setStartPrepare(boolean startPrepare) {
        this.startPrepare = startPrepare;
    }

    public int getBattleTime() {
        return battleTime;
    }

    public void setBattleTime(int battleTime) {
        this.battleTime = battleTime;
    }

    public boolean isStartBattle() {
        return startBattle;
    }

    public void setStartBattle(boolean startBattle) {
        this.startBattle = startBattle;
    }
}
