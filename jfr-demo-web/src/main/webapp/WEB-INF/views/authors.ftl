<!DOCTYPE html>
<html lang="en">
  <head>
    <title>Authors</title>
  </head>
  <body>
  <h2>Authors</h2>
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
    <#list authorModel.getEntries() as unit>
    do stuff with ${unit}
    </#list>
  </body>
</html>
