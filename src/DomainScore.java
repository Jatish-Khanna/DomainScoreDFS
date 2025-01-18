import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Since n (total unique domains) is typically less than or equal
 * to m (total domain strings), the total time complexity is:
 * <p>
 * The recursion depth is at most
 * the number of domain levels (the number of parts in a domain), which is O(k).
 * <p>
 * Total Time Complexity: O(m * k + n).
 * <p>
 * Thus, the space complexity for storing all the domains and subdomains is:
 * <p>
 * Space Complexity: O(n).
 */

public class DomainScore {

  static class Domain {
    String domainData;
    int score;
    Map<String, Domain> subdomains;

    Domain(String domainData, int score) {
      this.domainData = domainData;
      this.score = score;
      subdomains = new HashMap<>();
    }
  }


  private static void addSubdomains(Domain domain, Domain rootDomain) {
    Domain currentDomain = rootDomain;
    String[] allDomains = domain.domainData.split("\\."); // make sure to escape

    for (int index = allDomains.length - 1; index >= 0; index--) {
      if (!currentDomain.subdomains.containsKey(allDomains[index])) {
        currentDomain.subdomains.put(allDomains[index], new Domain(allDomains[index], 0));
      }
      currentDomain = currentDomain.subdomains.get(allDomains[index]);
    }
    currentDomain.score = domain.score;
  }


  private static int computeScore(Domain currentDomain, int pathScore) {
    // base case
    if (currentDomain.subdomains.isEmpty()) {
      return currentDomain.score + pathScore;
    }

    int score = 0;

    for (Domain domain : currentDomain.subdomains.values()) {
      score += computeScore(domain, currentDomain.score + pathScore);
    }

    return score;
  }


  public static void main(String[] args) {
    Domain rootDomain = new Domain("", 0);
    List<Domain> domainData = List.of(new Domain("google.com", 10), new Domain("maps.google.com", 5), new Domain("mail.google.com", 10), new Domain("yahoo.com", 1));

    for (Domain domain : domainData) {
      addSubdomains(domain, rootDomain);
    }

    if (rootDomain.subdomains.isEmpty()) {
      System.out.println("No domain/subdomains present: " + 0);
    }

    System.out.print("Computed score for the child: " + computeScore(rootDomain, 0));
  }
}