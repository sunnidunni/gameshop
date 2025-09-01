package cn.cie.services;

import cn.cie.entity.Kind;
import cn.cie.entity.dto.GameDTO;
import cn.cie.utils.Result;

import java.util.List;


public interface KindService {

    /**
     * Get category name by category id, return null if not exists
     * @param id
     * @return
     */
    String getNameById(Integer id);

    /**
     * Get all categories
     * @return
     */
    Result<Kind> getAll();

    /**
     * 根据种类获取所有的游戏，包括游戏信息和游戏的种类、标签
     * @param kind
     * @param page
     * @return
     */
    Result<List<GameDTO>> getGamesByKind(int kind, int page);

}
