# github-user-activity
A command-line interface that fetches a GitHub user's activity and displays it in the terminal.
# Features
The following repository events are fetched; **creation of repositories**, **forking**, **issue commenting**, **opening/closing issues**, **pulling** and **reviewing pulled requests**, **amount of commits pushed**, and **starring**. Any other user activities (such as deletion) are also supported, albeit with a simplified and generalised output.

This tool uses GitHub API integration, JSON object and array handling, along with HTTP request/response handling. Error handling is put in place to ensure a graceful handling of errors, such as invalid usernames and API failures. For learning how to use handle GitHub's API requests both the [GitHub Docs](https://docs.github.com/) and the popular API platform [Postman](https://www.postman.com/) were used.

:warning: Only events created within the past 90 days will be included, and 300 is the maximum total number of events. There is also possible latency of anywhere from 30 seconds to 6 hours due to the API's restriction.
# Additional information
This is a project created for [roadmap.sh](https://roadmap.sh/projects/github-user-activity).
