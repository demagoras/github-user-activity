import java.util.Scanner;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class GithubEvents {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User input for username
        System.out.print("Enter GitHub username: ");
        String username = scanner.nextLine();

        // Get API request
        HttpRequest request = HttpRequest.newBuilder()
                             .uri(URI.create("https://api.github.com/users/" + username + "/events"))
                             .header("Accept", "application/vnd.github+json")
                             .header("Authorization", "GITHUB_API_KEY")
                             .method("GET", HttpRequest.BodyPublishers.noBody())
                             .build();

        // Get API response
        try {
            HttpResponse<String> response = HttpClient.newHttpClient()
                                            .send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) { // Check if GitHub username is valid
                JSONArray jsonArray = new JSONArray(response.body());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonobject = jsonArray.getJSONObject(i);
                    String type = jsonobject.getString("type");
                    String repoName = jsonobject.getJSONObject("repo")
                                                .getString("name");

                    int pullRequest = 0;
                    if (type.equals("PullRequestReviewEvent") || type.equals("PullRequestEvent")) {
                        pullRequest = jsonobject.getJSONObject("payload")
                                                .getJSONObject("pull_request")
                                                .getInt("number");
                    }

                    // Check what type of event
                    switch (type) {
                        case "CreateEvent":
                            String refType = jsonobject.getJSONObject("payload").getString("ref_type");
                            System.out.println("- Created " + refType + " in " + repoName);
                            break;
                        case "ForkEvent":
                            System.out.println("- Forked " + repoName);
                            break;
                        case "IssueCommentEvent":
                            int payloadNumber = jsonobject.getJSONObject("payload").getJSONObject("issue").getInt("number");
                            System.out.println("- Commented on issue #" + payloadNumber);
                            break;
                        case "IssuesEvent":
                            String payloadAction = jsonobject.getJSONObject("payload")
                                                             .getString("action");
                            payloadAction = payloadAction.substring(0, 1).toUpperCase() // Capitalising first letter
                                          + payloadAction.substring(1);
                            System.out.println("- " + payloadAction + " a new issue in " + repoName);
                            break;
                        case "PullRequestReviewEvent":
                            System.out.println("- Reviewed pull request #" + pullRequest + " in " + repoName);
                            break;
                        case "PullRequestEvent":
                            System.out.println("- Created pull request #" + pullRequest + " in " + repoName);
                            break;
                        case "PushEvent":
                            int commitAmount = jsonobject.getJSONObject("payload")
                                                         .getJSONArray("commits")
                                                         .length();
                            System.out.println("- Pushed " + commitAmount + " commit(s) to " + repoName);
                            break;
                        case "WatchEvent":
                            System.out.println("- Starred " + repoName);
                            break;
                        default:
                            System.out.println("- " + type);
                            break;
                    }
                }
            } else {
                System.out.println("Invalid GitHub username.");
            } scanner.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
