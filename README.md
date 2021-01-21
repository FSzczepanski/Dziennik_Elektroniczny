# Dziennik_Elektroniczny

* made to practice Firebase authentication
* made to practice **MVVM approach** 
* Written in **Java**


# Dokumentacja na zajęcia:

# Aplikacja mobilna, Android Studio + Java, MVVM Pattern, Firebase authentication

# Identyfikacja raportu

**Nazwa** **przedmiotu**: Programowanie Aplikacji Mobilnych

**Grupa**: S2232

**Rok**: II (3 semestr)

**Osoby** **tworzące** **projekt**:

- Filip Szczepański  – 269391
- Mateusz Sobczyk  - 269396

# Cel projektu

Dziennik elektroniczny jest aplikacją mobilną pozwalającą na dodawanie własnych przedmiotów i ocen w celu ich przechowywania i obliczania średniej oraz mediany.

# Lista funkcjonalności

### Walidacja logowania

![Untitled](https://user-images.githubusercontent.com/61236736/105410811-cb11e580-5c32-11eb-8a30-83914f7ee3b2.png)

![Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%201.png](Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%201.png)

### Rejestracja i logowanie

![Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%202.png](Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%202.png)

Rejestracja oraz logowanie są maksymalnie uproszczone, po wpisaniu maila i hasła po naciśnięciu register rejestrujemy użytkownika, a następnie po naciśnięci login logujemy się.

```java
public class AuthFraghemnt extends Fragment {

    private AuthFraghemntViewModel mViewModel;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.auth_fraghemnt_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser, root);

        register(root);
        login(root);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AuthFraghemntViewModel.class);

    }

    public void updateUI(FirebaseUser account, View view) {

        if (account != null) {
            Log.d(TAG, "signIn:success");
            NavController navController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.my_nav_host_fragment);
            navController.navigate(R.id.action_authFraghemnt_to_mainPageFragment);

        } else {
            Log.w(TAG, "signIn:failure");
            Toast.makeText(getContext(), "Zaloguj się",
                    Toast.LENGTH_LONG).show();
        }

    }

    private void register(View view) {
        Button registerButton = view.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateForm(view)) {
                    return;
                }
                EditText emailEdit = view.findViewById(R.id.loginEmail);
                EditText passwordEdit = view.findViewById(R.id.loginPassword);
                String email = emailEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                createAccount(email,password);

            }
        });

    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Objects.requireNonNull(getActivity()), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            NavController navController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.my_nav_host_fragment);
                            navController.navigate(R.id.action_authFraghemnt_to_mainPageFragment);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void login(View view) {
        Button loginButton = view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateForm(view)) {
                    return;
                }

                EditText emailEdit = view.findViewById(R.id.loginEmail);
                EditText passwordEdit = view.findViewById(R.id.loginPassword);
                String email = emailEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                signIn(email,password);

            }
        });

    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Objects.requireNonNull(getActivity()), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            NavController navController = Navigation.findNavController(Objects.requireNonNull(getActivity()),
                                    R.id.my_nav_host_fragment);
                            navController.navigate(R.id.action_authFraghemnt_to_mainPageFragment);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    private boolean validateForm(View view) {
        boolean valid = true;

        EditText emailEdit = view.findViewById(R.id.loginEmail);
        EditText passwordEdit = view.findViewById(R.id.loginPassword);
        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getContext(), "Podaj prawidłową wartość email",
                    Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Podaj prawidłowe hasło",
                    Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }
```

## Strona główna

### Czysta, widok po rejestracji i pierwszym logowaniu

![Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%203.png](Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%203.png)

### Strona główna, uzupełniona

![Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%204.png](Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%204.png)

### Ekran ustawień

![Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%205.png](Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%205.png)

```java
public class SettingsFragment extends Fragment {

    private SettingsViewModel mViewModel;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.settings_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        TextView emailtext = root.findViewById(R.id.email);
        emailtext.setText(currentUser.getEmail());

        navigateBack(root);
        navigateToThemeSettings(root);
        signOut(root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        // TODO: Use the ViewModel
    }

    public void navigateBack(View root) {

        Button backButton = root.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final NavController navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
                navController.navigateUp();
            }
        });
    }

    public void navigateToThemeSettings(View root){
        View themeButton = root.findViewById(R.id.themeSettingsOnClick);
        themeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final NavController navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
                navController.navigate(R.id.action_settingsFragment_to_themeSettingsFragment);
            }
        });
    }

    public void signOut(View view){

        Button logoutButton = view.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                final NavController navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
                navController.navigate(R.id.action_settingsFragment_to_authFraghemnt);
            }
        });
```

## Theme settings

### Włączenie ciemnego motywu

![Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%206.png](Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%206.png)

![Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%207.png](Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%207.png)

```java
public class ThemeSettingsFragment extends Fragment {

    private ThemeSettingsViewModel mViewModel;

    public static ThemeSettingsFragment newInstance() {
        return new ThemeSettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.theme_settings_fragment, container, false);
        navigateBack(root);
        changeTheme(root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ThemeSettingsViewModel.class);
        // TODO: Use the ViewModel
    }

    public void changeTheme(View root){
        Button button = root.findViewById(R.id.themeChangeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button.getText().equals("Enable dark theme")){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    button.setText("Enable light theme");
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    button.setText("Enable dark theme");
                }
            }
        });
    }

    public void navigateBack(View root) {

        Button backButton = root.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final NavController navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
                navController.navigateUp();
            }
        });
    }

}
```

## Powrót na strone główną

![Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%208.png](Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%208.png)

## Wyświetlanie średniej oraz mediany dla ucznia

![Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%209.png](Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%209.png)

```java
private MainPageViewModel mViewModel;
private RecyclerView subjectRecycler;
private MainPageSubjectsAdapter subjectsAdapter;
private DialogFragment dialog;
private ArrayList myList;

public static MainPageFragment newInstance() {
    return new MainPageFragment();
}

@Override
public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.main_page_fragment, container, false);
    navigateToSettingsFragment(root);
    this.mViewModel = new ViewModelProvider(this).get(MainPageViewModel.class);
    subjectRecycler = root.findViewById(R.id.subjectsRecyclerView);
    showAddSubjectDialog(root);
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String userId = currentUser.getUid();

    this.mViewModel.getSubjects(userId,getContext()).observe(this, new Observer<List<Subject>>() {
        @Override
        public void onChanged(List<Subject> subjects) {
            Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");

            subjectsAdapter = new MainPageSubjectsAdapter(getActivity(), (ArrayList<Subject>) subjects, mViewModel);

            subjectRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            subjectRecycler.setAdapter(subjectsAdapter);
            // countAvg((ArrayList<Subject>) subjects);

            subjectsAdapter.notifyDataSetChanged(); //optional statement.
            myList = new ArrayList<Subject>();
            myList = (ArrayList) subjects;

        }
    });

    View viewCountAvg = root.findViewById(R.id.countAvg);
    viewCountAvg.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            countAvg(myList,root);
        }
    });

    return root;
}

@Override
public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

}

public void showAddSubjectDialog(View view) {
    Button buttonAddSubject = view.findViewById(R.id.addNewButton);
    buttonAddSubject.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            dialog = new AddSubjecItemDialog(mViewModel);
            dialog.show(getFragmentManager(), "DialogFragment");
            TextView uczen = view.findViewById(R.id.uczenText);

        }
    });
}

public void navigateToSettingsFragment(View root){
    View.OnClickListener s = Navigation.createNavigateOnClickListener(R.id.action_mainPageFragment_to_settingsFragment);
    ImageButton settingsButton = root.findViewById(R.id.settingsButton);
    settingsButton.setOnClickListener(s);

}

public void countAvg(ArrayList<Subject> subjects, View view) {

    if (subjects.size() > 0) {
        int size = subjects.size();
        //avg

        double sumOfAvg = 0;
        for (int i = 0; i < size; i++) {
            float sumOfGrades = 0;
            int countGrades = subjects.get(i).getGrades().size();
            for (int j = 0; j < countGrades; j++) {
                sumOfGrades += subjects.get(i).getGrades().get(j);
            }
            double subjectAvg = sumOfGrades / countGrades;
            sumOfAvg += subjectAvg;
        }
        double studentsGeneralAvg = sumOfAvg / size;

        TextView avgText = view.findViewById(R.id.sredniaText);
        DecimalFormat df = new DecimalFormat("##.###");
        avgText.setText("Your average:  " + df.format(studentsGeneralAvg));

        //median
        List<Float> allGrades = new ArrayList<Float>();
        for (int i = 0; i < size; i++) {
            int countGrades = subjects.get(i).getGrades().size();
            for (int j = 0; j < countGrades; j++) {
                allGrades.add(subjects.get(i).getGrades().get(j));
            }
        }
        Collections.sort(allGrades);
        int countAllGrades = allGrades.size();
        float median = 0;
        if (countAllGrades % 2 == 0) {
            int srodkowa = (countAllGrades - 2) / 2;
            float first = allGrades.get(srodkowa);
            float second = allGrades.get(srodkowa + 1);

            median = (first + second) / 2;
        } else {
            int srodkowa = (countAllGrades - 1) / 2;
            median = allGrades.get(srodkowa);
        }

        TextView medianText = view.findViewById(R.id.medianaText);
        medianText.setText("Your median:  " + median);
    } else {
        Toast.makeText(getActivity().getApplicationContext(), "You need subjects to calculate", Toast.LENGTH_SHORT).show();
    }
}
```

### Adapter dla przedmiotów, to on odpowiada za wyświetlanie, edycje oraz usuwanie przedmiotów

```java
public class MainPageSubjectsAdapter extends RecyclerView.Adapter<MainPageSubjectsAdapter.MyViewHolder> {
    Context context;
    private List<Subject> subjects;
    private MainPageViewModel mViewModel;
    private DialogFragment dialog;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.subject_row,parent,false);
        return new MainPageSubjectsAdapter.MyViewHolder(view);
    }

    public MainPageSubjectsAdapter(Context context, ArrayList<Subject> subjects, MainPageViewModel mViewModel) {
        this.context = context;
        this.subjects = subjects;
        this.mViewModel = mViewModel;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Subject currentItem = subjects.get(position);
        holder.name.setText(currentItem.getName());

        String myDate = convertDate(currentItem);
        holder.UpdatedAt.setText(myDate);

        String gradesConverted = convertGrades(currentItem);
        holder.grades.setText(gradesConverted);

        switch(currentItem.getImage()) {
            case 1:
                holder.myImage.setImageResource(R.drawable.z1);
                break;
            case 2:
                holder.myImage.setImageResource(R.drawable.z2);
                break;
            case 3:
                holder.myImage.setImageResource(R.drawable.z3);
                break;
            case 4:
                holder.myImage.setImageResource(R.drawable.z4);
                break;
            default:
                // code block
        }

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.deleteSubject(currentItem);
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new EditSubjectItemDialog(mViewModel,currentItem);
                dialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "DialogFragment");
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    private String convertDate(Subject currentItem){
        String date = currentItem.getUpdatedAt().toString();
        String[] tab = date.split("G");
        return tab[0];
    }

    private String convertGrades(Subject currentItem){
        String gradesConverted = "";

        for(float g: currentItem.getGrades()){
            String[] tab = Float.toString(g).split("");
            if (tab[2].equals("0")){
                gradesConverted +=tab[0]+"  ";
            }
            else{
                gradesConverted += g+"  ";
            }

        }
        return gradesConverted;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, UpdatedAt, grades;
        ImageView myImage;
        ImageButton deleteButton;
        FloatingActionButton editButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            UpdatedAt = itemView.findViewById(R.id.updatedAt);
            grades = itemView.findViewById(R.id.grades);
            myImage = itemView.findViewById(R.id.myImageview);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }
}
```

### Klasa widoku Strony głównej

```java
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".ui.mainPageFragment.MainPageFragment">
    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="55dp"
        android:background="@color/purple_500">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_marginStart="20dp"
            android:layout_marginTop="10dp"
            android:fontFamily="casual"
            android:text="@string/AppName"
            android:textColor="@color/white"
            android:textSize="20sp"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <ImageButton
            android:id="@+id/settingsButton"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="@null"
            android:src="@drawable/ic_baseline_settings_24"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintHorizontal_bias="0.958"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintVertical_bias="0.36" />
    </androidx.constraintlayout.widget.ConstraintLayout>
    <LinearLayout
        android:id="@+id/constV"
        android:layout_width="match_parent"
        android:layout_marginTop="7dp"
        android:layout_height="95dp"
        android:orientation="horizontal">

        <androidx.constraintlayout.widget.ConstraintLayout
            android:id="@+id/countAvg"
            android:layout_width="200dp"
            android:layout_height="95dp"
            android:layout_marginLeft="10dp"
            android:layout_marginTop="10dp"
            android:background="@color/gray"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintStart_toStartOf="parent">

            <TextView
                android:layout_width="250dp"
                android:layout_height="wrap_content"
                android:layout_marginStart="20dp"
                android:text="Click to calculate average and median"
                android:textColor="@color/black2"
                android:textSize="10sp"
                android:textStyle="bold"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintHorizontal_bias="0.0"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent"
                app:layout_constraintVertical_bias="0.037" />

            <TextView
                android:id="@+id/uczenText"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="20dp"
                android:text="Student"
                android:textColor="@color/black2"
                android:textSize="16sp"
                android:textStyle="bold"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintHorizontal_bias="0.0"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent"
                app:layout_constraintVertical_bias="0.232" />

            <TextView
                android:id="@+id/sredniaText"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="20dp"
                android:text="Avarage: "
                android:textColor="@color/black2"
                android:textSize="14sp"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintHorizontal_bias="0.0"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent"
                app:layout_constraintVertical_bias="0.513" />

            <TextView
                android:id="@+id/medianaText"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="20dp"
                android:text="Median: "
                android:textColor="@color/black2"
                android:textSize="14sp"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintHorizontal_bias="0.0"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent"
                app:layout_constraintVertical_bias="0.789" />
        </androidx.constraintlayout.widget.ConstraintLayout>

        <androidx.appcompat.widget.AppCompatButton
            android:id="@+id/addNewButton"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_marginLeft="5dp"
            android:background="@color/purple_200"
            android:text="Add new subject"
            android:layout_marginRight="10dp"
            android:layout_marginTop="10dp"
            android:textSize="11sp"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintHorizontal_bias="0.0"
            app:layout_constraintStart_toEndOf="@+id/countAvg"
            app:layout_constraintTop_toTopOf="parent" />

    </LinearLayout>

    <androidx.recyclerview.widget.RecyclerView
        android:layout_marginTop="10dp"
        android:id="@+id/subjectsRecyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:listitem="@layout/subject_row"/>

    <View
        android:layout_width="wrap_content"
        android:layout_height="1dp"
        android:background="@color/black"
        app:layout_constraintBottom_toTopOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent" />

</LinearLayout>
```

### Nawigacja Jetpack, pozwala na przemieszczanie się pomiędzy fragmentami i uproszczenie zarządzania ich cyklem życia

```java
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/navigation_graph"
    app:startDestination="@id/authFraghemnt">

    <fragment
        android:id="@+id/authFraghemnt"
        android:name="com.example.dziennikelektroniczny.ui.authFragment.AuthFraghemnt"
        android:label="AuthFraghemnt" >
        <action
            android:id="@+id/action_authFraghemnt_to_mainPageFragment"
            app:destination="@id/mainPageFragment"
            app:enterAnim="@anim/nav_default_enter_anim"/>
    </fragment>
    <fragment
        android:id="@+id/mainPageFragment"
        android:name="com.example.dziennikelektroniczny.ui.mainPageFragment.MainPageFragment"
        android:label="main_page_fragment"
        tools:layout="@layout/main_page_fragment" >
        <action
            android:id="@+id/action_mainPageFragment_to_settingsFragment"
            app:destination="@id/settingsFragment"
            app:enterAnim="@anim/nav_default_enter_anim"
            app:popUpTo="@+id/navigation_graph" />
    </fragment>
    <fragment
        android:id="@+id/settingsFragment"
        android:name="com.example.dziennikelektroniczny.ui.settingsFragment.SettingsFragment"
        android:label="settings_fragment"
        tools:layout="@layout/settings_fragment" >
        <action
            android:id="@+id/action_settingsFragment_to_authFraghemnt"
            app:destination="@id/authFraghemnt"
            app:launchSingleTop="true"
            app:popUpTo="@+id/navigation_graph"
            app:popUpToInclusive="true"/>
        <action
            android:id="@+id/action_settingsFragment_to_themeSettingsFragment"
            app:destination="@id/themeSettingsFragment" />
    </fragment>
    <fragment
        android:id="@+id/themeSettingsFragment"
        android:name="com.example.dziennikelektroniczny.ui.settingsFragment.ThemeSettings.ThemeSettingsFragment"
        android:label="theme_settings_fragment"
        tools:layout="@layout/theme_settings_fragment" />
</navigation>
```

## CRUD (Create, Read, Update Delete) oparty na MVVM pattern

### Układ projektu

![Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%2010.png](Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%2010.png)

### Dodawanie przedmiotu

![Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%2011.png](Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%2011.png)

![Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%2012.png](Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%2012.png)

![Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%2013.png](Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%2013.png)

![Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%2014.png](Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%2014.png)

### Kod Dialogu pobierającego dane przedmiotu:

```java
public class AddSubjecItemDialog extends DialogFragment {
    private MainPageViewModel mViewModel;
    private String name ="";
    private String grades= "";
    private DialogFragment dialog;

    public AddSubjecItemDialog(MainPageViewModel mViewModel) {
        this.mViewModel =mViewModel;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Add Subject");
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_subject_item, null);
        builder.setView(view);

        EditText editTextname = view.findViewById(R.id.etName);
        EditText editTextgrades = view.findViewById(R.id.etGrades);

        TextView textViewAdd = view.findViewById(R.id.tvAdd);
        TextView textViewCancel = view.findViewById(R.id.tvCancel);
        textViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editTextname.getText().toString();
                grades = editTextgrades.getText().toString();
                String[] tab = grades.split(",");

                if ((!name.matches("")) && tab.length>0) {
                    loadNextDialogAndPassData();
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "Enter correct data ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSubjecItemDialog.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    private void loadNextDialogAndPassData(){
        dialog = new AddSubjectPictureDialog(mViewModel);
        Bundle bundle = new Bundle();
        bundle.putString("aName", name);
        bundle.putString("aGrades", grades);
        dialog.setArguments(bundle);
        dialog.show(getFragmentManager(), "DialogFragment");
        AddSubjecItemDialog.this.getDialog().cancel();
    }

}
```

### Kod dialogu w którym wybieramy zdjęcie i dodajemy przedmiot do bazy danych.

```java
public class AddSubjectPictureDialog extends DialogFragment {
    private String name;
    private String grades;
    private MainPageViewModel mViewModel;

    public AddSubjectPictureDialog(MainPageViewModel mViewModel) {
        this.mViewModel = mViewModel;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_subject_image, null);
        builder.setView(view);

        if (getArguments() != null) {
            name = getArguments().getString("aName","");
            grades = getArguments().getString("aGrades","");
        }

        choosePicture(view);

        return builder.create();
    }

    private void choosePicture(View view){
        ImageButton button1 = view.findViewById(R.id.subjectPicture1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSubject(1);
                AddSubjectPictureDialog.this.getDialog().cancel();
            }
        });
        ImageButton button2 = view.findViewById(R.id.subjectPicture2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSubject(2);
                AddSubjectPictureDialog.this.getDialog().cancel();
            }
        });
        ImageButton button3 = view.findViewById(R.id.subjectPicture3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSubject(3);
                AddSubjectPictureDialog.this.getDialog().cancel();
            }
        });
        ImageButton button4 = view.findViewById(R.id.subjectPicture4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSubject(4);
                AddSubjectPictureDialog.this.getDialog().cancel();
            }
        });
    }

    private void createSubject(int image){
        Date date = new Date();

        ArrayList<Float> gradesArray = new ArrayList<Float>();
        String[]tab=grades.split(",");
        if (tab.length >= 2) {
            for(String a: tab){
                gradesArray.add(Float.parseFloat(a));
            }
        }

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userId = currentUser.getUid();

        Subject subject = new Subject(name,date,gradesArray,image,userId);
        mViewModel.insertSubject(subject);

    }

}
```

### Edycja przedmiotu

![Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%2015.png](Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%2015.png)

![Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%2016.png](Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%2016.png)

### Kod dialogu edycji przedmiotu:

```java
public class EditSubjectItemDialog extends DialogFragment{

    private MainPageViewModel mViewModel;
    private String name;
    private String grades;
    private DialogFragment dialog;
    private Subject subject;
    private EditText editTextname;
    private EditText editTextgrades;

    public EditSubjectItemDialog(MainPageViewModel mViewModel, Subject subject) {
        this.mViewModel =mViewModel;
        this.subject = subject;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Edit Subject");
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_subject_item, null);
        builder.setView(view);
        editTextname = view.findViewById(R.id.etName);
        editTextgrades = view.findViewById(R.id.etGrades);

        loadSubjectsDataAndDisplay();
        TextView textViewAdd = view.findViewById(R.id.tvAdd);
        textViewAdd.setText("EDIT");
        TextView textViewCancel = view.findViewById(R.id.tvCancel);

        textViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editTextname.getText().toString();
                grades = editTextgrades.getText().toString();
                if (!name.matches("")) {
                    editSubject();
                    EditSubjectItemDialog.this.getDialog().cancel();
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "Enter correct data ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditSubjectItemDialog.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    private void loadSubjectsDataAndDisplay(){
        editTextname.setText(subject.getName());
        String gradesStr = "";
        ArrayList<Float> gradesArray = subject.getGrades();

        for (int i = 0; i < gradesArray.size(); i++) {
            gradesStr+=Float.toString(gradesArray.get(i))+",";
        }
        editTextgrades.setText(gradesStr);
    }

    private void editSubject(){
        Date date = new Date();
        //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        ArrayList<Float> gradesArray = new ArrayList<Float>();
        String[]tab=grades.split(",");
        if (tab.length >= 2) {
            for(String a: tab){
                gradesArray.add(Float.parseFloat(a));
            }
        }
        int image = subject.getImage();

        subject.setName(name);
        subject.setGrades(gradesArray);
        mViewModel.updateSubject(subject);

    }

}
```

### Usuwanie przedmiotów

![Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%2017.png](Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%2017.png)

![Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%2018.png](Dziennik%20Elektroniczny%20050cd1b526fb4968b3d6b7099799c641/Untitled%2018.png)

## MVVM pattern

### Baza danych

```java
@androidx.room.Database(entities = {Subject.class}, version = 6, exportSchema = false)
@TypeConverters(DataConverter.class)
public abstract class Database extends RoomDatabase {
    private static final String DATABASE_NAME = "subjects_database";
    private static final String LOG_TAG = Database.class.getSimpleName();
    private static Database instance = null;
    public abstract SubjectDao subjectDao();

    public static Database getInstance(Context context) {
        if (instance == null) {
            Log.d(LOG_TAG, "Creating new database instance");
            instance = Room.databaseBuilder(context,
                    Database.class, Database.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries()
                    .build();
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return instance;
    }

}
```

### Encja przedmiotu

```java
@Entity(tableName = "subjects")
public class Subject {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private Date updatedAt;
    private ArrayList<Float> grades;
    private int image;
    private String userId;

    public Subject(String name, Date updatedAt, ArrayList<Float> grades,int image, String userId) {
        this.id=id;
        this.name = name;
        this.updatedAt = updatedAt;
        this.grades = grades;
        this.image = image;
        this.userId = userId;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public ArrayList<Float> getGrades() {
        return grades;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setGrades(ArrayList<Float> grades) {
        this.grades = grades;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
```

### Dao przedmiotu

```java
@Dao
public interface SubjectDao {
    @Insert
    void insert(Subject subject);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Subject subject);

    @Delete
    void delete(Subject subject);

    @Query("DELETE FROM subjects")
    void deleteAllSubjects();

    @Query("SELECT * FROM subjects where userId=:userId")
    LiveData<List<Subject>> loadAllSubjects(String userId);
}

d.repozytorium
public class SubjectsRepository {
    private LiveData<List<Subject>> subjects;
    private SubjectDao subjectDao;
    private Database database;
    private Context context;
    private String userId;

    public SubjectsRepository(Context context) {
        this.context = context;
        database = Database.getInstance(context);
    }
    public void insertSubject(Subject subject){
        database.subjectDao().insert(subject);
    }
    public LiveData<List<Subject>> getloadAllSubjects(String userId){
        subjects = database.subjectDao().loadAllSubjects(userId);
        return subjects;
    }

    public void updateSubject(Subject subject){
        database.subjectDao().update(subject);
    }

    public void deleteSubject(Subject subject){
        database.subjectDao().delete(subject);
    }
    public void deleteAllSubjects(){
        database.subjectDao().deleteAllSubjects();
    }

}
```

### View model strony głównej

```java
public class MainPageViewModel extends ViewModel {
    private LiveData<List<Subject>> subjects;
    private SubjectsRepository subjectsRepository;
    private Database database;
    private Application application;
    private String userId;

    public MainPageViewModel() {

    }

    public void insertSubject(Subject subject) {
        subjectsRepository.insertSubject(subject);
    }

    public LiveData<List<Subject>> getSubjects(String userId,Context context) {
        subjectsRepository = new SubjectsRepository(context);
        subjects = subjectsRepository.getloadAllSubjects(userId);
        return subjects;
    }

    public void updateSubject(Subject subject) {
        subjectsRepository.updateSubject(subject);
    }

    public void deleteSubject(Subject subject) {
        subjectsRepository.deleteSubject(subject);
    }

    public void deleteAllSubjects() {
        subjectsRepository.deleteAllSubjects();
    }

}
```

# **Dziękujemy, za uwagę.**

**Filip Szczepański**

**Mateusz Sobczyk**
