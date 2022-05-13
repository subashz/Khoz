package com.pie8.khoz.common.shared

import java.util.*

data class Session(

    var root: WebDirectory,
    var started: Date = Date(),
    var finished: Date = Date(),
    var httpStatusCode: MutableMap<Int, Int> = mutableMapOf(),
    var parameters: MutableMap<String, String> = mutableMapOf(),
    var description: String = "",
    var totalHttpTraffice: Long = 0L,
    var totalHttpRequests: Int = 0,
    var totalFiles: Int = 0,
    var totalFileSizeEstimated: Long = 0L,
    var errors: Int = 0,

    var maxThreads: Int = 0,
    var skipped: Int = 0,
    var stopLogging: Boolean = false,
    var uploadedUrlsUrl: String = "",
    var urlsWithErros: List<String> = listOf(),

    val processedUrls: MutableSet<String>? = Collections.synchronizedSet(mutableSetOf<String>()),

    var gdIndex: Boolean = false,
    var speedTestResult: SpeedTestResult? = null

)


/**

public WebDirectory Root { get; set; }
public DateTimeOffset Started { get; set; } = DateTimeOffset.MinValue;
public DateTimeOffset Finished { get; set; } = DateTimeOffset.MinValue;
public Dictionary<int, int> HttpStatusCodes { get; set; } = new Dictionary<int, int>();
public Dictionary<string, string> Parameters { get; set; } = new Dictionary<string, string>();
public string Description { get; set; }
public long TotalHttpTraffic { get; set; }
public int TotalHttpRequests { get; set; }
public int TotalFiles { get; set; }
public long TotalFileSizeEstimated { get; set; }
public int Errors { get; set; }
[JsonIgnore]
public int MaxThreads;
public int Skipped { get; set; }
[JsonIgnore]
public bool StopLogging { get; set; }
public string UploadedUrlsUrl { get; set; }
public List<string> UrlsWithErrors { get; set; } = new List<string>();
[JsonIgnore]
public ConcurrentSet<string> ProcessedUrls { get; set; } = new ConcurrentSet<string>();
[JsonIgnore]
public bool GDIndex { get; set; }
public SpeedtestResult SpeedtestResult { get; set; }
 */