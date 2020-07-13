```scala
type Jichao =
  "working on" --Language.Scala --Language.PureScript <|>
  "find me on"
    -- Social.Twitter["oyanglulu"]
    -- Social.Reddit["oyanglulu"]
    -- Social.Blog["https://blog.oyanglul.us"]
    -- Social.Publication["Grokking Monad", "https://gumroad.com/l/grokking-monad"]
    -- Social.Publication["前端函数式攻城指南", "https://m.douban.com/book/subject/26883736/"]

println(summon[ShowMarkdown[Jichao]].show)
```

## working on
- Scala
- PureScript
## find me on
- [Twitter](https://twitter.com/oyanglulu)
- [Reddit](https://reddit.com/oyanglulu)
- [Blog](https://blog.oyanglul.us)
- [Grokking Monad](https://gumroad.com/l/grokking-monad)
- [前端函数式攻城指南](https://m.douban.com/book/subject/26883736/)

<details>
  <summary>Where</summary>
<pre>
trait <|>[HasA, HasB]

trait --[Kind, Type]

enum Language {
  case Scala();
  case Haskell();
  case PureScript();
  case JavaScript();
  case TypeScript();
  case Clojure();
  case Ruby();
  case Python();
}

enum Social {
  case Twitter[Name]();
  case Blog[Url]();
  case Reddit[Name]();
  case Publication[Name, Url]();
}

trait ShowMarkdown[A] {
  def show: String
}

import scala.deriving._

given [A, B](using showA: ShowMarkdown[A], showB: ShowMarkdown[B]) as ShowMarkdown[--[A, B]] {
  def show = showA.show ++ "\n- " ++ showB.show
}

given [A, B](using showA: ShowMarkdown[A], showB: ShowMarkdown[B]) as ShowMarkdown[<|>[A, B]] {
  def show = s"""|## ${showA.show}
                 |## ${showB.show}""".stripMargin
}

given [Name <: String](using name: ValueOf[Name]) as ShowMarkdown[Social.Twitter[Name]] {
  def show = s"[Twitter](https://twitter.com/${name.value})"
}

given [Name <: String](using name: ValueOf[Name]) as ShowMarkdown[Social.Reddit[Name]] {
  def show = s"[Reddit](https://reddit.com/${name.value})"
}

given [Url <: String](using name: ValueOf[Url]) as ShowMarkdown[Social.Blog[Url]] {
  def show = s"[Blog](${name.value})"
}

given [Name <: String, Url <: String](using name: ValueOf[Name], url: ValueOf[Url]) as ShowMarkdown[Social.Publication[Name, Url]] {
  def show = s"[${name.value}](${url.value})"
}

given [R <: Language](using m: Mirror.Of[R], name: ValueOf[m.MirroredLabel]) as ShowMarkdown[R] {
  def show = name.value
}

given [S <: String](using m: ValueOf[S]) as ShowMarkdown[S] {
    def show: String = m.value
  }
</pre>
</details>
