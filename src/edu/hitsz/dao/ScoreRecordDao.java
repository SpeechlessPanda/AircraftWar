package edu.hitsz.dao;

import java.util.List;

/**
 * 排行榜数据访问对象接口。
 */
public interface ScoreRecordDao {

    // 返回存储中的原始记录顺序，通常给内部写回逻辑使用。
    List<ScoreRecord> getAll();

    // 返回按排行榜规则排序后的记录，供界面展示和名次删除使用。
    List<ScoreRecord> getAllSorted();

    void add(ScoreRecord scoreRecord);

    // rank 从 1 开始计数，对应用户在榜单里看到的名次。
    boolean deleteByRank(int rank);
}
