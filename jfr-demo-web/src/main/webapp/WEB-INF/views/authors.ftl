<!DOCTYPE html>
<html lang="en">
  <head>
    <title>Authors</title>
  </head>
  <body>
  <h2>Author Statistics</h2>
  <#list authorModel.getEntries()>
    <table>
    <caption>Authors</caption>
      <tr>
       <th>Author</th>
       <th>Articles</th>
       <th>Comments</th>
      </tr>
    <#items as author>
      <tr>
        <td>${author.authorName}</td>
        <td>${author.articleCount}</td>
        <td>${author.commentCount}</td>
      </tr>
    </#items>
    </table>
  </#list>
  </body>
</html>
