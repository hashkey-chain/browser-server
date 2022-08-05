package com.platon.browser.dao.custommapper;

import com.github.pagehelper.Page;
import com.platon.browser.bean.Erc1155ContractDestroyBean;
import com.platon.browser.bean.Token1155HolderListBean;
import com.platon.browser.bean.TokenIdListBean;
import com.platon.browser.dao.entity.Token1155Holder;
import com.platon.browser.dao.entity.Token1155HolderKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomToken1155HolderMapper {

    /**
     * 根据唯一索引查询
     *
     * @param token1155HolderKey:
     * @return: com.platon.browser.dao.entity.Token1155Holder
     * @date: 2022/8/3
     */
    Token1155Holder selectByUK(@Param("token1155HolderKey") Token1155HolderKey token1155HolderKey);

    /**
     * 批量新增或更新
     *
     * @param list:
     * @param selective:
     * @return: int
     * @date: 2022/8/3
     */
    int batchInsertOrUpdateSelective1155(@Param("list") List<Token1155Holder> list, @Param("selective") Token1155Holder.Column... selective);

    /**
     * 批量更新token持有者余额
     *
     * @param list:
     * @return: int
     * @date: 2022/8/3
     */
    int batchUpdate(@Param("list") List<Token1155Holder> list);

    /**
     * 查找销毁的合约
     *
     * @param type:
     * @return: java.util.List<com.platon.browser.bean.Erc1155ContractDestroyBean>
     * @date: 2022/8/3
     */
    List<Erc1155ContractDestroyBean> findDestroyContract(@Param("type") Integer type);

    /**
     * 查找合约下的持有者
     *
     * @param contract:
     * @return: com.github.pagehelper.Page<com.platon.browser.bean.Token1155HolderListBean>
     * @date: 2022/8/5
     */
    Page<Token1155HolderListBean> findToken1155HolderList(@Param("contract") String contract);

    /**
     * 库存列表
     *
     * @param key:
     * @return: com.github.pagehelper.Page<com.platon.browser.bean.TokenIdListBean>
     * @date: 2022/8/5
     */
    Page<TokenIdListBean> findTokenIdList(@Param("key") Token1155HolderKey key);

}