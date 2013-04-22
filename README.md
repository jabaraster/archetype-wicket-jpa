Wicet + JPA (+ JERSEY) での開発環境の雛形です.

# 提供形態
Mavenのarchetype(プロジェクトの雛形)です.  

# Mavenの導入
予めMavenをセットアップしておいて下さい.  
下記のサイトからZIPをダウンロードし、ZIPを解凍した中身を適当な場所に置き、PATHを通しておいて下さい.  
<http://maven.apache.org/>

# 利用方法
次のコマンドを打つと、Wicket + JPA + JERSEY(JAX-RS実装)がセットアップされたプロジェクトが作成されます.  

```
mvn -B archetype:generate -DgroupId=sandbox -DartifactId=Aad -Dpackage=sandbox.aad -DarchetypeCatalog=http://jabaraster.github.io/maven/archetype-catalog.xml -DarchetypeGroupId=jabaraster -DarchetypeArtifactId=archetype-wicket-jpa
```


