package cn.cie.services;

import cn.cie.entity.dto.GameDTO;
import cn.cie.utils.Result;

import java.util.List;


public interface GameService {

    /**
     * Get game details by game id
     * @param id
     * @return
     */
    Result<GameDTO> getById(Integer id);

    /**
     * Get random images for daily recommendations
     * @return
     */
    Result<List<GameDTO>> getRandomGames();

    /**
     * Get latest 5 games from cache, if not available get from database
     * @return
     */
    Result<List<GameDTO>> newestGames();

    /**
     * Get latest 5 games ready for release from cache, if not available get from database
     * @return
     */
    Result<List<GameDTO>> preUpGames();

    /**
     * Search from categories, tags, and game information
     * @param info
     * @return
     */
    Result<List<GameDTO>> search(String info);

    /**
     * Get free games
     * @return
     */
    Result getFreeGames();

    /**
     * Check if game exists
     * @param id
     * @return
     */
    boolean exists(Integer id);
}
