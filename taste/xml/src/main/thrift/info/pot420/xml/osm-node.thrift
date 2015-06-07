namespace java info.pot420.xml.thriftjava
#@namespace scala info.pot420.xml.thriftscala

struct OsmNode {
  1: i64 id;
  2: double lat;
  3: double lon;
  4: map<string, string> attributes;
}