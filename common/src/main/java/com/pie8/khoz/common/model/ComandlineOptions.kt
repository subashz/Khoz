package com.pie8.khoz.common.model


data class CommandLineOptions(
//    //[Option('u', "url", Required = false, HelpText = "Url to scan")]
    val url: String,

    //[Option('t', "threads", Required = false, Default = 5, HelpText = "Number of threads")]
    val threads: Int,

    //[Option('o', "timeout", Required = false, Default = 100, HelpText = "Number of seconds for timeout")]
    val timeOut: Int,

    //[Option('q', "quit", Required = false, Default = false, HelpText = "Do not wait after scanning")]
    val quit: Boolean,

    //[Option('c', "clipboard", Required = false, Default = false, HelpText = "Copy Reddit stats after scanning")]
//    public bool Clipboard { get; set; }
    val clipBoard: Boolean,

    //[Option('j', "json", Required = false, Default = false, HelpText = "Save JSON file")]
//    public bool Json { get; set; }
    val json: Boolean,

    //[Option('f', "no-urls", Required = false, Default = false, HelpText = "Do not save URLs file")]
//    public bool NoUrls { get; set; }
    val noUrls: Boolean,

    //[Option("no-browser", Required = false, Default = false, HelpText = "Do not launch browser (for cloudflare etc.)")]
//    public bool NoBrowser { get; set; }
    val noBrowser: Boolean,
    //[Option('r', "no-reddit", Required = false, Default = false, HelpText = "Do not show Reddit stats markdown")]
//    public bool NoReddit { get; set; }
    val noReddit: Boolean,
    //[Option('e', "exact-file-sizes", Required = false, Default = false, HelpText = "Exact file sizes (WARNING: Uses HEAD requests which takes more time and is heavier for server)")]
//    public bool ExactFileSizes { get; set; }
    val exactFileSize: Boolean,
    //[Option('l', "upload-urls", Required = false, Default = false, HelpText = "Uploads urls file")]
//    public bool UploadUrls { get; set; }
    val uploadUrls: Boolean,
    //[Option('s', "speedtest", Required = false, Default = false, HelpText = "Do a speed test")]
//    public bool Speedtest { get; set; }
    val speedTest: Boolean,
    //[Option('a', "user-agent", Required = false, HelpText = "Use custom default User Agent")]
//    public string UserAgent { get; set; }
    val userAgent: String,
    //[Option("username", Required = false, Default = "", HelpText = "Username")]
//    public string Username { get; set; }
    val userName: String,
    //[Option("password", Required = false, Default = "", HelpText = "Password")]
//    public string Password { get; set; }
    val password: String,
    //[Option("output-file", Required = false, Default = null, HelpText = "Save output files to specific base filename")]
//    public string OutputFile { get; set; }
    val outfile: String,
    //[Option("fast-scan", Required = false, Default = false, HelpText = "Only perform actions that are fast, so no HEAD requests, etc. Might result in missing file sizes")]
//    public bool FastScan { get; set; }
    val fastScan: Boolean,
    //[Option("proxy-address", Required = false, Default = "", HelpText = "Proxy address, like: socks5://127.0.0.1:9050")]
//    public string ProxyAddress { get; set; }
    val proxyAddress: String,
    //[Option("proxy-username", Required = false, Default = "", HelpText = "Proxy username")]
//    public string ProxyUsername { get; set; }
    val proxyUserName: String,
    //[Option("proxy-password", Required = false, Default = "", HelpText = "Proxy password")]
//    public string ProxyPassword { get; set; }
    val proxyPassword: String,
    //[Option('H', "header", Required = false, Default = null, HelpText = "Provide a custom header to use for any HTTP request while indexing. Option can be used multiple times for multiple headers.")]
//    public IEnumerable<string> Header { get; set; }
    val header: List<String>,
    // TODO: Future use
    ////[Option('d', "download", Required = false, HelpText = "Downloads the contents (after indexing is finished)")]
    //public bool Download { get; set; }
    val download: Boolean,
)