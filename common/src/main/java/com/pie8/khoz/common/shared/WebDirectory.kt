package com.pie8.khoz.common.shared

import io.ktor.http.*
import java.net.URI
import java.util.*

class WebDirectory {
    var parentDirectory: WebDirectory? = null

    constructor()

    constructor(webDirectory: WebDirectory) {
        parentDirectory = webDirectory
    }


    var url: String = ""
        set(value) {
            uri = URI(value)
            field = value
        }

    var uri: URI = URI.create(url)

    var name: String = ""

    var description: String = ""

    var finished: Boolean = false

    var parsedSuccessfully: Boolean = false

    val subdirectories = Collections.synchronizedList(mutableListOf<WebDirectory>())
    val files = Collections.synchronizedList(mutableListOf<WebFile>())

    val error: Boolean = false

    var totalFileSize: Long =
        subdirectories.sumOf { it.totalFileSize } + files.sumOf { it.fileSize };

    var totalFiles: Int = subdirectories.sumOf { it.totalFiles } + files.count()


    var totalDirectories: Int =
        subdirectories.sumOf { it.totalDirectories } + subdirectories.count { it.finished }

    var totalDirectoriesIncludingUnfinished: Int =
        subdirectories.sumOf { it.totalDirectories } + subdirectories.count()

    var urls: List<String> = files.map { it.url }

    var allFileUrls: List<String> = subdirectories.flatMap { it.allFileUrls } + (url)
//    public IEnumerable<string> AllFileUrls => Subdirectories.SelectMany(sd => sd.AllFileUrls).Concat(Urls).OrderBy(url => url);

    var allFiles: List<WebFile> = subdirectories.flatMap { it.allFiles } + files
//    public IEnumerable<WebFile> AllFiles => Subdirectories.SelectMany(sd => sd.AllFiles).Concat(Files);

    var parser: String = ""

    var startTime: Date? = null
    var finishTime: Date? = null

    var headerCount: Int = 0

    var cancellationReason = ""

}


/**

[DebuggerDisplay("{Name,nq}, Directories: {Subdirectories.Count,nq}, Files: {Files.Count,nq}")]
public class WebDirectory
{
public WebDirectory(WebDirectory parentWebDirectory)
{
ParentDirectory = parentWebDirectory;
}

[JsonIgnore]
public WebDirectory ParentDirectory { get; set; }

public string Url { get; set; }

[JsonIgnore]
public Uri Uri => new Uri(Url);

public string Name { get; set; }

public string Description { get; set; }

public bool Finished { get; set; }

[JsonIgnore]
public bool ParsedSuccessfully { get; set; }

public ConcurrentList<WebDirectory> Subdirectories { get; set; } = new ConcurrentList<WebDirectory>();

public ConcurrentList<WebFile> Files { get; set; } = new ConcurrentList<WebFile>();

public bool Error { get; set; }

[JsonIgnore]
public long TotalFileSize => Subdirectories.Sum(sd => sd.TotalFileSize) + Files.Sum(f => f.FileSize);

[JsonIgnore]
public int TotalFiles => Subdirectories.Sum(sd => sd.TotalFiles) + Files.Count;

[JsonIgnore]
public int TotalDirectories => Subdirectories.Sum(sd => sd.TotalDirectories) + Subdirectories.Count(sd => sd.Finished);

[JsonIgnore]
public int TotalDirectoriesIncludingUnfinished => Subdirectories.Sum(sd => sd.TotalDirectories) + Subdirectories.Count;

[JsonIgnore]
public IEnumerable<string> Urls => Files.Select(f => f.Url);

[JsonIgnore]
public IEnumerable<string> AllFileUrls => Subdirectories.SelectMany(sd => sd.AllFileUrls).Concat(Urls).OrderBy(url => url);

[JsonIgnore]
public IEnumerable<WebFile> AllFiles => Subdirectories.SelectMany(sd => sd.AllFiles).Concat(Files);

[JsonIgnore]
public string Parser { get; set; } = string.Empty;

[JsonIgnore]
public DateTimeOffset StartTime { get; set; }
[JsonIgnore]
public DateTimeOffset FinishTime { get; set; }

[JsonIgnore]
public int HeaderCount { get; set; }

[JsonIgnore]
public string CancellationReason { get; set; }
}
 **/