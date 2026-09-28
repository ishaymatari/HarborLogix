# TYPE_ANALYSIS.md — ניתוח טיפוס סטטי מול טיפוס דינמי

**שם:** ישי מטרי  **ת.ז. / מספר סטודנט:** 216774570

הנחיות: בחרו **שמונה** שורות **מהקוד שכתבתם בעצמכם** — לא מקוד שסופק לכם.

| # | קובץ:שורה | הביטוי | טיפוס סטטי | טיפוס דינמי | מי מכריע — קומפיילר או אובייקט? | נימוק במשפט אחד |
|---|-----------|--------|-------------|--------------|-------------------------------|------------------|
| 1 | `Yard.java:34` | `u.dailyStorageFee()` | `CargoUnit` | `StandardContainer` / `RefrigeratedContainer` / `HazmatContainer` / `LiquidTank` | אובייקט (JVM) | שיגור דינמי שבו המתוֹדה שתורץ נקבעת בזמן ריצה לפי הטיפוס הדינמי הממשי של המטען. |
| 2 | `Yard.java:44` | `unit.safetyBriefing()` | `CargoUnit` | `HazmatContainer` / `RefrigeratedContainer` / `LiquidTank` | אובייקט (JVM) | שיגור דינמי המפעיל את הנחיות הבטיחות הספציפיות שנדרסו בכל מחלקה נגזרת. |
| 3 | `RefrigeratedContainer.java:22` | `super.dailyStorageFee()` | `StandardContainer` | `RefrigeratedContainer` | קומפיילר | שימוש ב-`super` קובע קריאה מפורשת למימוש במחלקת האב כבר בזמן הקומפילציה. |
| 4 | `TerminalApp.java:43` | `(LiquidTank) unit` | `CargoUnit` | `LiquidTank` | קומפיילר (בדיקת תחביר) / אובייקט (בדיקת תקינות בזמן ריצה) | ההמרה למטה (downcast) משנה את הטיפוס הסטטי מ-`CargoUnit` ל-`LiquidTank`. |
| 5 | `TerminalApp.java:45` | `tank.drain(2000)` | `LiquidTank` | `LiquidTank` | קומפיילר | אילו הטיפוס הסטטי היה `CargoUnit`, הקומפיילר היה פוסל את הקריאה כי ל-`CargoUnit` אין מתוֹדה `drain`. |
| 6 | `Yard.java:60` | `client.discountPercent()` | `Client` | `ContractClient` / `GovernmentClient` | אובייקט (JVM) | קריאה פולימורפית המחשבת את אחוז ההנחה לפי הטיפוס הדינמי של הלקוח בזמן ריצה. |
| 7 | `TerminalApp.java:51` | `CargoUnit oversized = new OversizedCargo(...)` | `CargoUnit` | `OversizedCargo` | קומפיילר | הקומפיילר מאשר השמה של טיפוס נגזר למשתנה מטיפוס אבסטרקטי. |
| 8 | `Yard.java:24` | `unit.getId()` | `CargoUnit` | `StandardContainer` / `LiquidTank` / etc. | אובייקט (JVM) | המתוֹדה שמופעלת נקבעת לפי המופע הדינמי של המטען שנבדק במערכת. |

---

### שאלה משלימה א׳
ב־`TerminalApp` השתמשתם ב־`instanceof LiquidTank` בסבב הניקוז,
אך ב־`Yard` השימוש ב־`instanceof` אסור לחלוטין.
הסבירו מדוע ההמרה הזו מוצדקת דווקא שם — ומה הייתם צריכים לשנות
כדי להיפטר ממנה לגמרי.

**תשובה:**
השימוש ב-`instanceof` מוצדק ב-`TerminalApp` מכיוון שהוא מציג לוגיקה ייעודית וחיצונית של תסריט הדגמה/סבב תחזוקה ייחודי למכלי נוזלים בלבד, מבלי להשפיע על ניהול החצר המרכזי. לעומת זאת, ב-`Yard` השימוש ב-`instanceof` אסור מכיוון ש-`Yard` מנהלת את המטענים באופן כולללי ושימוש ב-`instanceof` היה מפר את עקרון הפתוח/סגור (OCP) ומחייב שינוי של `Yard` בכל הוספת סוג מטען חדש.

כדי להיפטר מה-`instanceof` ב-`TerminalApp` לגמרי, היינו יכולים להגדיר ממשק (Interface) כגון `Drainable` או להוסיף מתוֹדה אבסטרקטית/ברירת מחדל ב-`CargoUnit` (למשל `performMaintenance()`), כך שכל מטען יבצע לוגיקת תחזוקה פולימורפית משלו (מכלי נוזלים ינקזו נוזלים, ומטענים אחרים לא יעשו דבר או יבצעו בדיקה מתאימה).

---

### שאלה משלימה ב׳
העתיקו שורה אחת מהקוד שלכם שבה **הקומפיילר** בוחר את החתימה
ו**האובייקט** בוחר את המימוש. סמנו איזה חלק בשורה מוכרע על ידי מי.

**תשובה:**
שורה מהקוד (`Yard.java:60`):
`double discount = client.discountPercent();`

* **הקומפיילר מוכרע על ידי הטיפוס הסטטי (`Client`):** הקומפיילר בודק שבמחלקה `Client` קיימת חתימת מתוֹדה בשם `discountPercent()` המחזירה `double` ללא ארגומנטים.
* **האובייקט (JVM) מוכרע על ידי הטיפוס הדינמי בזמן ריצה:** ה-JVM קובע איזה מימוש בפועל יורץ – של `Client` (0%), של `ContractClient` (15%), או של `GovernmentClient` (25%).
