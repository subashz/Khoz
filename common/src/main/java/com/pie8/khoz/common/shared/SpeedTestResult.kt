package com.pie8.khoz.common.shared

data class SpeedTestResult(
    var downloadedBytes: Long = 0L,
    var elapsedMilliseconds: Long = 0L,
    var maxMBsPerSecondval: Double = 0.0,
    var maxKBsPerSecondval: Double = 0.0,
    var maxBytesPerSecond: Long = 0L
) {
    var downloadedMBs = downloadedBytes / 1024 / 1024
    var downloadedKBs = downloadedBytes / 1024

    var MBsPerSecond = downloadedMBs / (elapsedMilliseconds / 1000);
    var MbitsPerSecond = downloadedMBs / (elapsedMilliseconds / 1000) * 8
    var KBsPerSecond = downloadedKBs / (elapsedMilliseconds / 1000);
    var KbitsPerSecond = downloadedKBs / (elapsedMilliseconds / 1000) * 8

}


/**
 * 	public long DownloadedBytes { get; set; }
public double DownloadedMBs => DownloadedBytes / 1024d / 1024d;
public double DownloadedKBs => DownloadedBytes / 1024d;
public long ElapsedMilliseconds { get; set; }
public double MBsPerSecond => DownloadedMBs / (ElapsedMilliseconds / 1000d);
public double MbitsPerSecond => DownloadedMBs / (ElapsedMilliseconds / 1000d) * 8;
public double KBsPerSecond => DownloadedKBs / (ElapsedMilliseconds / 1000d);
public double KbitsPerSecond => DownloadedKBs / (ElapsedMilliseconds / 1000d) * 8;
public double MaxMBsPerSecond { get; set; }
public double MaxKBsPerSecond { get; set; }
public long MaxBytesPerSecond { get; set; }
 */