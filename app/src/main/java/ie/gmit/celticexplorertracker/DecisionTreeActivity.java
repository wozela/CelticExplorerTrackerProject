package ie.gmit.celticexplorertracker;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class DecisionTreeActivity extends ActionBarActivity {

    private LinearLayout myDynamic_layout;
    private Button btnCurRoot;
    private Node root = new Node("Back Button", null);
    private Node currentNode = root;
    private Button btnDelete;
    private Button btnAdd;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision_tree);

        myDynamic_layout = (LinearLayout) findViewById(R.id.my_dynamic_layout);
        btnCurRoot = (Button) findViewById(R.id.btnCurrentRoot);
        btnCurRoot.setText(currentNode.getName());

        //build the tree
        treeBuild();
        //set root
        setRoot();
        //set children of current root
        setChildrenCurrentRoot();

        //remove node
        btnRemoveNode();
        //add new node
        btnAddNewNode();

        writeObjectToFile(root);



    }

    private void btnRemoveNode() {
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);

                View promptView = layoutInflater.inflate(R.layout.delete_prompt, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptView);
                // input from user and delete node at position
                final EditText input = (EditText) promptView.findViewById(R.id.txtNodeID);
                alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // get user input delete a node at the position stated by user
                        int i = input.getInputType();
                        currentNode.deleteChild(root.getChild(i));
                        refreshActivity();
                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                // alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });
    }

    private void btnAddNewNode() {
        btnAdd = (Button) findViewById(R.id.btnAdd);
        // Click to show alert dialog and get node name from user
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("InflateParams")
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View promptView = layoutInflater.inflate(R.layout.add_prompt, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptView);
                final EditText input = (EditText) promptView.findViewById(R.id.txtNodeName);

                alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // get node name from the uer and setup new node
                        input.getText();
                        Node n = new Node(input.getText().toString(), currentNode);
                        currentNode.addChild(n);
                        refreshActivity();
                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,	int id) {
                                dialog.cancel();
                            }
                        });
                // alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

            }

            public void treeBuild() {

                //create nodes
                Node solid = new Node("Solid", root);
                Node flexible = new Node("Flexible", (Node) root);
                Node hard = new Node("Hard", solid);
                Node smooth = new Node("Smooth", hard);
                Node irregularEdges = new Node("IrregularEdges", hard);
                Node squashed = new Node("Squashed", solid);
                Node styrene = new Node("Styrene", squashed);
                Node resin = new Node("Resin", smooth);
                Node fragmentOfItem = new Node("FragmentOfItem", irregularEdges);
                Node cylindrical = new Node("Cylindrical", resin);
                Node rounded = new Node("Rounded", resin);
                Node longish = new Node("Long", cylindrical);
                Node flat = new Node("Flat", cylindrical);
                Node oval = new Node("Oval", rounded);
                Node sphere = new Node("Sphere", rounded);
                Node edges = new Node("Edges", fragmentOfItem);
                Node allAngular = new Node("AllAngular", edges);
                Node mostlyAngular = new Node("MostlyAngular", edges);
                Node mostlyRound = new Node("MostlyRound", edges);
                Node allRound = new Node("AllRound", edges);
                Node filamentous = new Node("Filamentous", flexible);
                Node large2DArea = new Node("large2DArea", flexible);
                Node fiber = new Node("Fiber", filamentous);
                Node film = new Node("Film", large2DArea);

                //add children
                root.addChild(solid);
                root.addChild(flexible);
                solid.addChild(hard);
                solid.addChild(squashed);
                squashed.addChild(styrene);
                hard.addChild(smooth);
                hard.addChild(irregularEdges);
                smooth.addChild(resin);
                irregularEdges.addChild(fragmentOfItem);
                resin.addChild(cylindrical);
                resin.addChild(rounded);
                cylindrical.addChild(longish);
                cylindrical.addChild(flat);
                rounded.addChild(oval);
                rounded.addChild(sphere);
                fragmentOfItem.addChild(edges);
                edges.addChild(mostlyAngular);
                edges.addChild(allAngular);
                edges.addChild(mostlyRound);
                edges.addChild(allRound);
                flexible.addChild(filamentous);
                flexible.addChild(large2DArea);
                filamentous.addChild(fiber);
                large2DArea.addChild(film);
            }

            public void setRoot() {
                if (root != null) {
                    //get parent of current root
                    btnCurRoot.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            if (currentNode != root) {
                                currentNode = currentNode.getParent();
                                myDynamic_layout.removeAllViews();
                                refreshActivity();
                            } else {
                                Toast.makeText(DecisionTreeActivity.this, "Start", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            private void setChildrenCurrentRoot() {
                for (int j = 0; j < currentNode.childrenAmt(); j++) {
                    final Button childName = new Button(this);
                    final int BtnId = j;
                    childName.setText(currentNode.getChild(j).getName());
                    //Add Children to layout
                    myDynamic_layout.addView(childName);
                    childName.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            currentNode = currentNode.getChild(BtnId);
                            refreshActivity();
                            //Toast.makeText(SearchActivity.this, "Id: " + BtnId, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.menu_decision_tree, menu);
                return true;
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                // Handle action bar item clicks here. The action bar will
                // automatically handle clicks on the Home/Up button, so long
                // as you specify a parent activity in AndroidManifest.xml.
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.action_settings) {
                    return true;
                }

                return super.onOptionsItemSelected(item);
            }

            public void refreshActivity() {
                myDynamic_layout.removeAllViews();
                btnCurRoot = (Button) findViewById(R.id.btnCurrentRoot);
                btnCurRoot.setText(currentNode.getName());
                setChildrenCurrentRoot();
            }

            public void writeObjectToFile(Node root2file) {
                ObjectOutputStream out;
                try {
                    out = new ObjectOutputStream(new FileOutputStream("Tree.csv"));
                    //serialization
                    out.writeObject(root2file);
                    out.flush();
                    out.close();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
}