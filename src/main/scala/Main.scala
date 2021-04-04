trait <>[HasA, HasB]

trait --[Kind, Type]

enum Language:
  case Scala();
  case Haskell();
  case PureScript();
  case JavaScript();
  case TypeScript();
  case Clojure();
  case Ruby();
  case Python();

enum Social:
  case Twitter[Name]();
  case Reddit[Name]();
  case Keybase[Name]();

trait Link[Name, Url]

trait ShowMarkdown[A]:
  def show: String

import scala.deriving._

given [A, B](using showA: ShowMarkdown[A], showB: ShowMarkdown[B]): ShowMarkdown[--[A, B]]  with 
  def show = showA.show ++ "\n- " ++ showB.show

given [A, B](using showA: ShowMarkdown[A], showB: ShowMarkdown[B]): ShowMarkdown[<>[A, B]] with
  def show = s"""|${showA.show}
                 |## ${showB.show}""".stripMargin

given [Name <: String](using name: ValueOf[Name]): ShowMarkdown[Social.Twitter[Name]] with
  def show = s"[Twitter](https://twitter.com/${name.value})"

given [Name <: String](using name: ValueOf[Name]): ShowMarkdown[Social.Reddit[Name]] with
  def show = s"[Reddit](https://reddit.com/${name.value})"

given [Url <: String](using name: ValueOf[Url]): ShowMarkdown[Social.Keybase[Url]] with
  def show = s"[Keybase proofs](https://keybase.io/${name.value})"

given [Name <: String, Url <: String](using name: ValueOf[Name], url: ValueOf[Url]): ShowMarkdown[Link[Name, Url]] with
  def show = s"[${name.value}](${url.value})"

given [R <: Language](using m: Mirror.Of[R], name: ValueOf[m.MirroredLabel]): ShowMarkdown[R] with
  def show = name.value

given [S <: String](using m: ValueOf[S]): ShowMarkdown[S] with
    def show: String = m.value

type Jichao =  "" <>
  "used to work on"
    -- Language.JavaScript
    -- Language.Clojure
    -- Language.Ruby
    -- Language.Python
    -- Language.Haskell <>
  "working on" --Language.Scala --Language.PureScript <>
  "me also on"
    -- Social.Twitter["oyanglulu"]
    -- Social.Reddit["oyanglulu"]
    -- Social.Keybase["oyanglulu"]
    -- Link["Blog", "https://blog.oyanglul.us"]
    -- Link["GPG public keys", "https://github.com/jcouyang.gpg"]
    -- Link["SSH public keys", "https://github.com/jcouyang.keys"]
    -- Link["Grokking Monad", "https://gumroad.com/l/grokking-monad"]
    -- Link["前端函数式攻城指南", "https://m.douban.com/book/subject/26883736/"]

@main def hello: Unit =
  println(summon[ShowMarkdown[Jichao]].show)
