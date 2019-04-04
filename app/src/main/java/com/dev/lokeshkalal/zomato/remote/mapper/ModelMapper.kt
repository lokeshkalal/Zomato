package com.dev.lokeshkalal.zomato.remote.mapper

interface ModelMapper<M, E> {

    fun mapModelFromResponse(model: M): E

}