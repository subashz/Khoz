package com.pie8.khoz.common.shared

data class OpenDirectory(
    var url: String,
    var finished: Boolean,
    var root: WebDirectory
)


//public string Url { get; set; }
//public bool Finished { get; set; }
//public WebDirectory Root { get; set; }