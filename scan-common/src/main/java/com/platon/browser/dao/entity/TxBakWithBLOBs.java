package com.platon.browser.dao.entity;

import java.util.ArrayList;
import java.util.Arrays;

public class TxBakWithBLOBs extends TxBak {
    private String input;

    private String info;

    private String erc1155TxInfo;

    private String erc721TxInfo;

    private String erc20TxInfo;

    private String transferTxInfo;

    private String pposTxInfo;

    private String failReason;

    private String method;

    private String bin;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input == null ? null : input.trim();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public String getErc1155TxInfo() {
        return erc1155TxInfo;
    }

    public void setErc1155TxInfo(String erc1155TxInfo) {
        this.erc1155TxInfo = erc1155TxInfo;
    }

    public String getErc721TxInfo() {
        return erc721TxInfo;
    }

    public void setErc721TxInfo(String erc721TxInfo) {
        this.erc721TxInfo = erc721TxInfo == null ? null : erc721TxInfo.trim();
    }

    public String getErc20TxInfo() {
        return erc20TxInfo;
    }

    public void setErc20TxInfo(String erc20TxInfo) {
        this.erc20TxInfo = erc20TxInfo == null ? null : erc20TxInfo.trim();
    }

    public String getTransferTxInfo() {
        return transferTxInfo;
    }

    public void setTransferTxInfo(String transferTxInfo) {
        this.transferTxInfo = transferTxInfo == null ? null : transferTxInfo.trim();
    }

    public String getPposTxInfo() {
        return pposTxInfo;
    }

    public void setPposTxInfo(String pposTxInfo) {
        this.pposTxInfo = pposTxInfo == null ? null : pposTxInfo.trim();
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason == null ? null : failReason.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin == null ? null : bin.trim();
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table tx_bak
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        id("id", "id", "BIGINT", false),
        hash("hash", "hash", "VARCHAR", false),
        bHash("b_hash", "bHash", "VARCHAR", false),
        num("num", "num", "BIGINT", false),
        index("index", "index", "INTEGER", true),
        time("time", "time", "TIMESTAMP", true),
        nonce("nonce", "nonce", "VARCHAR", false),
        status("status", "status", "INTEGER", true),
        gasPrice("gas_price", "gasPrice", "VARCHAR", false),
        gasUsed("gas_used", "gasUsed", "VARCHAR", false),
        gasLimit("gas_limit", "gasLimit", "VARCHAR", false),
        from("from", "from", "VARCHAR", true),
        to("to", "to", "VARCHAR", true),
        value("value", "value", "VARCHAR", true),
        type("type", "type", "INTEGER", true),
        cost("cost", "cost", "VARCHAR", false),
        toType("to_type", "toType", "INTEGER", false),
        seq("seq", "seq", "BIGINT", false),
        creTime("cre_time", "creTime", "TIMESTAMP", false),
        updTime("upd_time", "updTime", "TIMESTAMP", false),
        contractType("contract_type", "contractType", "INTEGER", false),
        contractAddress("contract_address", "contractAddress", "VARCHAR", false),
        input("input", "input", "LONGVARCHAR", true),
        info("info", "info", "LONGVARCHAR", false),
        erc1155TxInfo("erc1155_tx_info", "erc1155TxInfo", "LONGVARCHAR", false),
        erc721TxInfo("erc721_tx_info", "erc721TxInfo", "LONGVARCHAR", false),
        erc20TxInfo("erc20_tx_info", "erc20TxInfo", "LONGVARCHAR", false),
        transferTxInfo("transfer_tx_info", "transferTxInfo", "LONGVARCHAR", false),
        pposTxInfo("ppos_tx_info", "pposTxInfo", "LONGVARCHAR", false),
        failReason("fail_reason", "failReason", "LONGVARCHAR", false),
        method("method", "method", "LONGVARCHAR", true),
        bin("bin", "bin", "LONGVARCHAR", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table tx_bak
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table tx_bak
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table tx_bak
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table tx_bak
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table tx_bak
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table tx_bak
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table tx_bak
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table tx_bak
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table tx_bak
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table tx_bak
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table tx_bak
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table tx_bak
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table tx_bak
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table tx_bak
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table tx_bak
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }
    }
}