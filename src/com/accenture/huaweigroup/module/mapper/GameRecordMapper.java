package com.accenture.huaweigroup.module.mapper;

import com.accenture.huaweigroup.module.entity.GameRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRecordMapper {

    void insert(GameRecord gameRecord);

    //xml配置文件未更新
//    void update(GameRecord gameRecord);

    GameRecord findByPlayerId(int playerOneId, int playerTwoId);

    GameRecord findByGameId(int gameId);
}
