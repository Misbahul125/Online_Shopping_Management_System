function initiateOrder(pid , amount , paymentMethod) {
    console.log("order initiated...");
    console.log(pid);
    console.log(amount);
    console.log(paymentMethod);
    
    
}







const initiatePayment = () => {
    console.log("payment initiated-1");
    let amount = $("#amt").val();
    console.log(amount);

    if (amount === "" || amount === null) {
        //alert("Amount cannot be empty");
        swal("Field empty", "Amount is required", "error");
        return;
    }

    $.ajax({
        url: "PaymentOperationServlet",
        data: {amount: amount,
            info: "order_request"},
        type: 'POST',
        dataType: 'json',
        success: function (response) {

            console.log(response);
            if (response.status == "created") {
                //open payment form

                let options = {
                    "key": "rzp_test_XZmCccoknEaBos",
                    "amount": response.amount,
                    "currency": "INR",
                    "name": "OSMS-The All New Shopping App",
                    "description": "Test Transaction",
                    "image": "https://static.vecteezy.com/system/resources/previews/003/275/721/large_2x/shopping-bag-store-logo-online-shopping-logo-design-free-vector.jpg",
                    "order_id": response.id,
                    "handler": function (response) {

                        console.log(response.razorpay_order_id);
                        console.log(response.razorpay_payment_id);
                        console.log(response.razorpay_signature);
                        console.log("Payment successful");
                        swal("Payment successful!", "Your order have been placed successfully.", "success");
                    },
                    "prefill": {

                        "name": "",
                        "email": "",
                        "contact": ""
                    },
                    "notes": {
                        "address": "You are paying for your order",
                    },
                    "theme": {
                        "color": "#E91E63",
                    },
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
                    swal("Payment failed!", "Something went wrong. Your order cannot be placed.", "error");
                });

                rzp.open();

            }
        },
        error: function (error) {
            console.log(error);
            swal("Payment failed!", "Something went wrong. Your payment cannot be initiated.", "error");
        }

    }
    );

};


