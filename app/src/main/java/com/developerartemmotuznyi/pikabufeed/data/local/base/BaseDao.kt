package com.developerartemmotuznyi.pikabufeed.data.local.base

import com.developerartemmotuznyi.pikabufeed.core.ActionResult

interface BaseDao

suspend inline fun <T : BaseDao, DataType> T.execute(block: T.() -> DataType): ActionResult<DataType> =
    try {
        ActionResult.Success(block())
    } catch (e: Exception) {
        ActionResult.Error(e)
    }