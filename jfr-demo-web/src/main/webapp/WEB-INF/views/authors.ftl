<!DOCTYPE html>
<html lang="en">
  <head>
    <title>Authors</title>
  </head>
  <body>
  <#list authorModel.entries>
    <table>
      <tr>
       <th>Author</ht>
       <th>Articles</ht>
       <th>Comments</ht>
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
