package com.pie8.khoz.common.shared

data class WebFile(
    var url: String,
    var fileName: String,
    var fileSize: Long,
    var description: String
)


/**
 * 	public string Url { get; set; }
public string FileName { get; set; }
public long FileSize { get; set; }
public string Description { get; set; }
 */