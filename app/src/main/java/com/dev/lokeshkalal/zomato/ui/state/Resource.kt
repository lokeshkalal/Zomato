package com.dev.lokeshkalal.zomato.ui.state

class Resource<out T> constructor(val resourceState: ResourceState, val data: T?, val message: String?) {
}