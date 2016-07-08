Android's Linkify class doesn't handle custom schemes. For example:

```
The custom url in this sentence would never get turned into a clickable link: custom://www.google.com
```

See `MainActivity.java` on how to make it work.