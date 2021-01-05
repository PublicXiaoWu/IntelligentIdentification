package com.lsx


/**
 * @Description:    身份证信息
 * @CreateDate:     2021/1/4
 * @Author:         LSX
 */
data class IDCard(
        val address: String,
        val birth: String,
        val cardNumber: String,
        val name: String,
        val nation: String,
        val sex: String
)

