package com.developerartemmotuznyi.pikabufeed.data.network.base

import com.developerartemmotuznyi.pikabufeed.core.ActionResult


interface BaseApi

suspend inline fun <T : BaseApi, DataType> T.execute(crossinline block: suspend T.() -> DataType): ActionResult<DataType> =
    try {
        ActionResult.Success(block())
    } catch (e: Exception) {
        ActionResult.Error(e)
    }