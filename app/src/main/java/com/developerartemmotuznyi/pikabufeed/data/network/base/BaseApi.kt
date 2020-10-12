package com.developerartemmotuznyi.pikabufeed.data.network.base

import com.developerartemmotuznyi.pikabufeed.core.ActionResult


interface BaseApi

inline fun <T : BaseApi, DataType> T.execute(block: T.() -> DataType): ActionResult<DataType> =
    try {
        ActionResult.Success(block())
    } catch (e: Exception) {
        ActionResult.Error(e)
    }