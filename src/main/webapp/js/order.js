async function getOrderId(data) {
    try {
        const reqOptions = {
            method: "post",
            body: JSON.stringify(data)
        };
        const res = await fetch("PaymentOperationServlet", reqOptions);
        const response = await res.json();
        return response;

    } catch (e) {
        console.log(e);
        Swal.fire({
            icon: 'error',
            title: 'Payment failed!',
            text: 'Something went wrong. Your payment cannot be initiated.'
        });
    }

}

//this api call is for inserting data into orders table
async function onSuccessHandler(response) {
    try {
        const reqOptions = {
            method: "post",
            body: JSON.stringify(response)
        };
        console.log(response);
        const res = await fetch("OrderOperationServlet", reqOptions);
        const r2 = await res.json();
        console.log(r2);

        if (r2.success) {
            Swal.fire({
                title: 'Success',
                text: 'Your order have been placed successfully. Click OK to generate invoice page.',
                icon: 'success',
//                showCancelButton: true,
//                cancelButtonColor: red,
                focusConfirm: true,
                confirmButtonColor: '#E91E63',
                confirmButtonText: 'OK'
            }).then((result) => {
                if (result.isConfirmed) {
                    location = "/OnlineShoppingManagementSystem/invoice.jsp";
                }
            });
            //window.location.replace("/OnlineShoppingManagementSystem/invoice.jsp");
        } else {
            console.log(r2.message);
            Swal.fire({
                icon: 'error',
                title: 'Payment Failed!',
                text: r2.message + " If your amount is deducted, it will be credited back within 10 working days."
            });
        }
    } catch (e) {
        console.log(e);
        Swal.fire({
            icon: 'error',
            title: 'Order failed!',
            text: 'Something went wrong. If your amount is deducted, it will be credited back within 10 working days.'
        });
    }
}
;

async function initiateOrder(pid, amount, paymentMethod, dDate) {
    console.log("order initiated1");
    console.log(pid);
    console.log(amount);
    console.log(paymentMethod);

    //for onine payment
    if (paymentMethod === 1) {

        const response = await getOrderId({
            productId: pid,
            amount: amount,
            paymentMethod: paymentMethod
        });

        if (response.status === "created") {

            let options = {
                "key": "rzp_test_XZmCccoknEaBos",
                "amount": response.amount,
                "currency": "INR",
                "name": "OSMS-The All New Shopping App",
                "description": "Test Transaction",
                "image": "https://static.vecteezy.com/system/resources/previews/003/275/721/large_2x/shopping-bag-store-logo-online-shopping-logo-design-free-vector.jpg",
                "order_id": response.id,
                "handler": async (data) => {
                    console.log("this is data from razorpay payment");
                    console.log(data);
                    await onSuccessHandler({
                        amount: amount,
                        paymentMethod: paymentMethod,
                        pid: pid,
                        deliveryDate: dDate,
                        ...data
                    });
                },
                "prefill": {

                    "name": "",
                    "email": "",
                    "contact": ""
                },
                "notes": {
                    "address": "You are paying for your order"
                },
                "theme": {
                    "color": "#E91E63"
                }
            };

            var rzp = new Razorpay(options);

            rzp.on('payment.failed', function (response) {
                console.log(response.error.code);
                console.log(response.error.description);
                console.log(response.error.source);
                console.log(response.error.step);
                console.log(response.error.reason);
                console.log(response.error.metadata.order_id);
                console.log(response.error.metadata.payment_id);
                alert("Oops! Payment failed");
                Swal.fire({
                    icon: 'error',
                    title: 'Payment failed!',
                    text: response.error.reason
                });
            });

            rzp.open();


        } else {
            Swal.fire({
                icon: 'error',
                title: 'Payment failed!',
                text: response.message
            });
        }

    }

    //for cash on delivery
    else {

        const response = await onSuccessHandler({
            amount: amount,
            paymentMethod: paymentMethod,
            pid: pid,
            deliveryDate: dDate
        });

    }
}






// const initiateOrder = (pid, amount, paymentMethod) => {
//    console.log("payment initiated-1");
//    //let amount = $("#amt").val();
//    console.log(amount);

//    if (amount === "" || amount === null) {
//        //alert("Amount cannot be empty");
//        swal("Field empty", "Amount is required", "error");
//        return;
//    }

//    $.ajax({
//        url: "PaymentOperationServlet",
//        data: {amount: amount,
//            info: "order_request"},
//        type: 'POST',
//        dataType: 'json',
//        success: function (response) {

//            console.log(response);
//            if (response.status == "created") {
//                //open payment form

//                console.log("hello");

//                let options = {
//                    "key": "rzp_test_XZmCccoknEaBos",
//                    "amount": response.amount,
//                    "currency": "INR",
//                    "name": "OSMS-The All New Shopping App",
//                    "description": "Test Transaction",
//                    "image": "https://static.vecteezy.com/system/resources/previews/003/275/721/large_2x/shopping-bag-store-logo-online-shopping-logo-design-free-vector.jpg",
//                    "order_id": response.id,
//                    "handler": function (response) {

//                        console.log(response.razorpay_order_id);
//                        console.log(response.razorpay_payment_id);
//                        console.log(response.razorpay_signature);
//                        console.log("Payment successful");
//                        swal("Payment successful!", "Your order have been placed successfully.", "success");
//                    },
//                    "prefill": {

//                        "name": "",
//                        "email": "",
//                        "contact": ""
//                    },
//                    "notes": {
//                        "address": "You are paying for your order",
//                    },
//                    "theme": {
//                        "color": "#E91E63",
//                    },
//                };

//                var rzp = new Razorpay(options);

//                rzp.on('payment.failed', function (response) {
//                    console.log(response.error.code);
//                    console.log(response.error.description);
//                    console.log(response.error.source);
//                    console.log(response.error.step);
//                    console.log(response.error.reason);
//                    console.log(response.error.metadata.order_id);
//                    console.log(response.error.metadata.payment_id);
//                    alert("Oops! Payment failed");
//                    swal("Payment failed!", "Something went wrong. Your order cannot be placed.", "error");
//                });

//                rzp.open();

//            }
//        },
//        error: function (error) {
//            console.log(error);
//            swal("Payment failed!", "Something went wrong. Your payment cannot be initiated.", "error");
//        }

//    }
//    );

// };


