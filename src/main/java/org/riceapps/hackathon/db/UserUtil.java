package org.riceapps.hackathon.db;

import java.sql.ResultSet;

import lightning.db.MySQLDatabase;
import lightning.db.NamedPreparedStatement;
import lightning.plugins.cas.CASUser;
import lightning.users.User;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

public class UserUtil {
  private final MySQLDatabase db;
  private final User user;

  public UserUtil(MySQLDatabase db, User user) {
    this.db = db;
    this.user = user;
  }

  /**
   * @return Returns an optional that resolves with the CAS username of the CAS account linked to the
   *         authenticated user (if any).
   * @throws Exception
   */
  public Optional<String> getLinkedNetID() throws Exception {
    try (
      NamedPreparedStatement query = db.prepare("SELECT * FROM cas_links WHERE user_id = :user_id LIMIT 1;",
          ImmutableMap.of("user_id", user.getId()));
      ResultSet result = query.executeQuery();
    ) {
      if (result.next()) {
        return Optional.of(result.getString("cas_user"));
      }
    }

    return Optional.absent();
  }

  /**
   * @return Whether or not the account is linked to a CAS account.
   * @throws Exception
   */
  public boolean isLinkedToNetID() throws Exception {
    return getLinkedNetID().isPresent();
  }

  /**
   * Unlinks the authenticated user from any CAS accounts.
   * @throws Exception
   */
  public void unlinkFromNetID() throws Exception {
    db.prepare("DELETE FROM cas_links WHERE user_id = :user_id;",
        ImmutableMap.of("user_id", user.getId()))
        .executeUpdateAndClose();
  }

  /**
   * Links the authenticated user to the given CAS account.
   * @param user The CAS user to link to.
   * @return Whether or not the linking was successful.
   * @throws Exception
   */
  public boolean linkToNetID(CASUser user) throws Exception {
    return db.transaction(() -> {
      try (
        NamedPreparedStatement query = db.prepare("SELECT * FROM cas_links WHERE cas_user = :cas_user LIMIT 1;",
            ImmutableMap.of("cas_user", user.username));
        ResultSet result = query.executeQuery();
      ) {
        if (result.next()) {
          return false; // Already bound to another user.
        }
      }

      db.prepareInsert("cas_links", ImmutableMap.of(
            "cas_user", user.username,
            "user_id", this.user.getId()
          ))
          .executeUpdateAndClose();

      return true;
    });
  }

  public boolean isOnDomain(String domain) {
    return user.getEmail().endsWith("@" + domain);
  }

  public boolean isAutoAccepted() throws Exception {
    return isFromHostUniversity() || isOnDomain("uh.edu");
  }

  public boolean isFromHostUniversity() throws Exception {
    return isLinkedToNetID() || isOnDomain("rice.edu");
  }
}
