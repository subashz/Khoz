package com.pie8.khoz.common.model

data class DirectoryListingModel01(

//    [JsonProperty(PropertyName = "name")]
//public string Name { get; set; }
    val name: String,

//[JsonProperty(PropertyName = "type")]
//public string Type { get; set; }
    val type: String,
//[JsonProperty(PropertyName = "path")]
//public string Path { get; set; }
    val path: String,
//[JsonProperty(PropertyName = "items")]
//public List<DirectoryListingModel01> Items { get; set; }
    val items: List<DirectoryListingModel01>,
//[JsonProperty(PropertyName = "size")]
//public long Size { get; set; }
    val size: Long
)