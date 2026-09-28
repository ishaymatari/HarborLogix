# EXTENSION.md — דוח מבחן ההרחבה (חלק ד׳)

**שם:** ישי מטרי  **ת.ז. / מספר סטודנט:** 216774570

---

### 1. רשימת כל הקבצים ששיניתם או יצרתם בחלק ד׳

| קובץ | נוצר / שונה | מה בדיוק השתנה |
|------|--------------|-----------------|
| `OversizedCargo.java` | נוצר | מחלקה חדשה היורשת מ-`CargoUnit` ומממשת תעריף שטוח לפי `TariffPolicy.OVERSIZE_DAILY_FLAT`. |
| `TerminalApp.java` | שונה | הוספת קליטה של אובייקט `OversizedCargo` והדפסת המניפסט המעודכן בחלק ד'. |

> אם `Yard.java` מופיע בטבלה — התכן שלכם אינו עומד בעקרון הפתוח/סגור.
> הסבירו מדוע נאלצתם לשנות אותו וכיצד הייתם מתקנים את התכן.

*(הערה: `Yard.java` לא שונה כלל ונשאר נקי לחלוטין, בהתאם לעקרון הפתוח/סגור).*

---

### 2. פלט ההרצה

הדביקו כאן את המניפסט **לפני** ו**אחרי** קליטת ה־`OversizedCargo`.

לפני:
```text
=== 1. YARD MANIFEST ===
--- Yard Manifest: Main Yard ---
Standard[id=U101, owner=Standard Shipping Co, 12000.0 kg, 5 days, vol=33.2m3] | Safety: Standard container: Ensure standard twist-lock securing on deck/yard.
Reefer[id=U102, owner=Global Logistics Ltd, 15000.0 kg, 3 days, vol=30.0m3, temp=-18.0C, kw=5.5] | Safety: Reefer: Maintain power grid connection. Target temp: -18.0 C.
Hazmat[id=U103, owner=Ministry of Defense, 8000.0 kg, 2 days, vol=25.0m3, hazClass=3, escort=true] | Safety: HAZMAT Class 3: Handle with protective gear. Escort required: true.
Tank[id=U104, owner=Standard Shipping Co, 20000.0 kg, 4 days, cap=10000L, fill=80.0%] | Safety: Liquid Tank: Verify valve seals and pressure relief systems before transfer.
Total Daily Revenue: 1089.70

```

אחרי:
```
=== 4. PART D: EXTENSION DEMONSTRATION ===
--- Yard Manifest: Main Yard (After Oversized Cargo) ---
Standard[id=U101, owner=Standard Shipping Co, 12000.0 kg, 5 days, vol=33.2m3] | Safety: Standard container: Ensure standard twist-lock securing on deck/yard.
Reefer[id=U102, owner=Global Logistics Ltd, 15000.0 kg, 3 days, vol=30.0m3, temp=-18.0C, kw=5.5] | Safety: Reefer: Maintain power grid connection. Target temp: -18.0 C.
Hazmat[id=U103, owner=Ministry of Defense, 8000.0 kg, 2 days, vol=25.0m3, hazClass=3, escort=true] | Safety: HAZMAT Class 3: Handle with protective gear. Escort required: true.
Tank[id=U104, owner=Standard Shipping Co, 20000.0 kg, 4 days, cap=10000L, fill=80.0%] | Safety: Liquid Tank: Verify valve seals and pressure relief systems before transfer.
Oversized[id=U105, owner=Global Logistics Ltd, 35000.0 kg, 1 days, flatFee=100.0] | Safety: Oversized Load: Ensure heavy-lift crane and wide-clearance route inside yard.
Total Daily Revenue: 1189.70
```

---

### 3. שאלת ההבנה
`Yard.printManifest()` הדפיסה שורה תקינה עבור `OversizedCargo`
למרות ש־`Yard` מעולם לא שמעה על המחלקה הזו.
הסבירו במדויק **מתי** הוחלט איזה קוד ירוץ עבור השורה
`unit.handlingCategory()` — בזמן קומפילציה או בזמן ריצה — ומה בדיוק
הקומפיילר כן ידע באותו רגע.

תשובה:

ההחלטה איזה קוד ירוץ בפועל עבור unit.handlingCategory() התקבלה בזמן ריצה (Runtime) באמצעות מנגנון Dynamic Dispatch (ריבוביות דינמית/פולימורפיזם).בזמן הקומפילציה (Compile-time), הקומפיילר ידע אך ורק את הטיפוס הסטטי (Static Type) של המשתנה, שהוא CargoUnit. הקומפיילר וידא שבמחלקה האבסטרקטית CargoUnit אכן מוגדרת המתוֹדה handlingCategory(), וכך אישר שהקריאה תקינה תחבירית. אולם, הקומפיילר לא ידע איזה טיפוס דינמי (Dynamic Type) של מטענים יימצא בתוך הרשימה בזמן הרצה.   בזמן הריצה, ה-JVM בדק את הטיפוס הדינמי הממשי של האובייקט שהפנה אליו unit (במקרה זה, מופע של OversizedCargo), והפעיל את המימוש הספציפי שנדרס במחלקה זו.
