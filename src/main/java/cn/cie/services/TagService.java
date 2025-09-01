package cn.cie.services;

import cn.cie.entity.Tag;
import cn.cie.entity.dto.GameDTO;
import cn.cie.utils.Result;

import java.util.List;


public interface TagService {

    /**
     * Get tag name by tag id, return null if not exists
     * @param id
     * @return
     */
    String getNameById(Integer id);

    /**
     * Get all tags
     * @return
     */
    Result<List<Tag>> getAll();

    /**
     * Add tag without binding to game
     * @param name
     * @return
     */
    Result<Tag> addTag(String name);

    /**
     * Add tag and bind to game
     * @param name
     * @param game
     * @return
     */
    Result addTag(String name, Integer game);

    /**
     * Bind tag to game
     * @param tag
     * @param game
     * @return
     */
    Result addTag(Integer tag, Integer game);

    /**
     * Get all games by tag
     * @param tag
     * @param page
     * @return
     */
    Result<List<GameDTO>> getGamesByTag(Integer tag, Integer page);

}
